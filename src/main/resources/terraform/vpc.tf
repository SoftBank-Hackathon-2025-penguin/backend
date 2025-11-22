# VPC
resource "aws_vpc" "main" {
  cidr_block           = "10.0.0.0/16"
  enable_dns_hostnames = true
  enable_dns_support   = true

  tags = {
    Name = "${var.project_name}-${var.session_id}-vpc"
  }
}

# Internet Gateway
resource "aws_internet_gateway" "main" {
  vpc_id = aws_vpc.main.id

  tags = {
    Name = "${var.project_name}-${var.session_id}-igw"
  }
}

# Public Subnet
resource "aws_subnet" "public" {
  vpc_id                  = aws_vpc.main.id
  cidr_block              = "10.0.1.0/24"
  availability_zone       = "${var.aws_region}a"
  map_public_ip_on_launch = true

  tags = {
    Name = "${var.project_name}-${var.session_id}-public-subnet"
    Type = "public"
  }
}

# ⚡ Private Subnet 제거 (생성 시간 5초 단축)
# Lambda가 VPC를 사용하지 않으므로 Private Subnet 불필요
# 모든 리소스는 Public Subnet에서 충분

# resource "aws_subnet" "private" {
#   vpc_id            = aws_vpc.main.id
#   cidr_block        = "10.0.2.0/24"
#   availability_zone = "${var.aws_region}a"
#
#   tags = {
#     Name = "${var.project_name}-${var.session_id}-private-subnet"
#     Type = "private"
#   }
# }

# Route Table for Public Subnet
resource "aws_route_table" "public" {
  vpc_id = aws_vpc.main.id

  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.main.id
  }

  tags = {
    Name = "${var.project_name}-${var.session_id}-public-rt"
  }
}

# Route Table Association for Public Subnet
resource "aws_route_table_association" "public" {
  subnet_id      = aws_subnet.public.id
  route_table_id = aws_route_table.public.id
}

# ===================================================================
# ⚡ NAT Gateway 제거 (생성 시간 1분+ 단축)
# ===================================================================
# Lambda가 VPC를 사용하지 않으므로 NAT Gateway 불필요
# Private Subnet도 실질적으로 미사용
#
# 제거된 리소스:
# - aws_eip.nat (NAT Gateway용 Elastic IP)
# - aws_nat_gateway.main (생성 시간 60-90초)
# - aws_route_table.private (NAT 라우팅)
#
# 예상 효과:
# - 생성 시간: 2분 30초 → 1분 30초 (40% 단축)
# - 비용: $32/월 절감
# ===================================================================


# Route Table Association for Private Subnet
# ⚡ Private Route Table Association 제거 (Private Subnet 미사용)
# resource "aws_route_table_association" "private" {
#   subnet_id      = aws_subnet.private.id
#   route_table_id = aws_route_table.private.id
# }

# Security Group for EC2
resource "aws_security_group" "ec2" {
  name        = "${var.project_name}-${var.session_id}-ec2-sg"
  description = "Security group for Spring Boot EC2 instance"
  vpc_id      = aws_vpc.main.id

  # HTTP
  ingress {
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
    description = "HTTP"
  }

  # HTTPS
  ingress {
    from_port   = 443
    to_port     = 443
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
    description = "HTTPS"
  }

  # Spring Boot App Port
  ingress {
    from_port   = 8080
    to_port     = 8080
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
    description = "Spring Boot Application"
  }

  # SSH (선택적, 키페어가 있을 때만 사용)
  ingress {
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
    description = "SSH"
  }

  # Outbound: 모든 트래픽 허용
  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
    description = "Allow all outbound traffic"
  }

  tags = {
    Name = "${var.project_name}-${var.session_id}-ec2-sg"
  }
}

# Security Group for Lambda
resource "aws_security_group" "lambda" {
  name        = "${var.project_name}-${var.session_id}-lambda-sg"
  description = "Security group for Lambda functions"
  vpc_id      = aws_vpc.main.id

  # Outbound: 모든 트래픽 허용
  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
    description = "Allow all outbound traffic"
  }

  tags = {
    Name = "${var.project_name}-${var.session_id}-lambda-sg"
  }
}
