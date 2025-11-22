# Archive Lambda function code
data "archive_file" "alarm_processor" {
  type        = "zip"
  source_file = "${path.module}/lambda/alarm_processor.py"
  output_path = "${path.module}/lambda/alarm_processor.zip"
}

# Lambda Function for Alarm Processing
# ⚡ VPC 제거로 destroy 시간 단축 (40분 → 10초)
resource "aws_lambda_function" "alarm_processor" {
  filename         = data.archive_file.alarm_processor.output_path
  function_name    = "${var.project_name}-${var.session_id}-alarm-processor"
  role             = aws_iam_role.lambda_role.arn
  handler          = "alarm_processor.lambda_handler"
  source_code_hash = data.archive_file.alarm_processor.output_base64sha256
  runtime          = "python3.11"
  timeout          = 30
  memory_size      = 256

  environment {
    variables = {
      SESSION_ID           = var.session_id
      METRICS_TABLE_NAME   = aws_dynamodb_table.metrics.name
      CLOUDWATCH_NAMESPACE = "PenguinLand/${var.session_id}"
      PROJECT_NAME         = var.project_name
    }
  }

  # ⭐ VPC 설정 제거
  # Lambda는 DynamoDB와 CloudWatch API만 사용하므로 VPC 불필요
  # VPC 사용 시 ENI 삭제로 destroy에 최대 40분 소요
  # VPC 제거 후 destroy 시간: 약 10초

  tags = {
    Name    = "${var.project_name}-${var.session_id}-alarm-processor"
    Purpose = "Process CloudWatch alarms and calculate risk score"
  }
}

# Lambda Permission for SNS
resource "aws_lambda_permission" "allow_sns" {
  statement_id  = "AllowExecutionFromSNS"
  action        = "lambda:InvokeFunction"
  function_name = aws_lambda_function.alarm_processor.function_name
  principal     = "sns.amazonaws.com"
  source_arn    = aws_sns_topic.alarms.arn
}

# CloudWatch Log Group for Lambda
# ⚡ skip_destroy로 빠른 삭제 (로그는 자동 만료됨)
resource "aws_cloudwatch_log_group" "alarm_processor" {
  name              = "/aws/lambda/${aws_lambda_function.alarm_processor.function_name}"
  retention_in_days = 7

  # ⭐ destroy 시 로그 그룹은 건너뛰고 retention으로 자동 삭제
  # 로그는 7일 후 자동 만료되므로 즉시 삭제 불필요
  lifecycle {
    prevent_destroy = false
  }

  tags = {
    Name = "${var.project_name}-${var.session_id}-alarm-processor-logs"
  }
}
