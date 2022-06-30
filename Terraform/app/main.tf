module "ec2Angular" {
  source              = "../Modules/EC2s/EC2Angular"
  author_name         = var.author_name
  instance_type       = var.instance_type
  private_key_path    = var.private_key_path
  availability_zone   = var.ec2_avail_zone
  sg_name             = module.sg.out-sg-name
  public_ip           = module.eip.out_eip_ip
  main_directory      = var.main_dir
  subnet_id           =aws_subnet.public.id
  vpc_security_group_ids = [module.sg.sg-id]
}
 module "ec2Compte" {
  source              = "../Modules/EC2s/EC2Compte"
  author_name         = var.author_name
  instance_type       = var.instance_type
  private_key_path    = var.private_key_path
  availability_zone   = var.ec2_avail_zone
  sg_name             = module.sg.out-sg-name
  public_ip           = module.eip.out_eip_ip
  main_directory      = var.main_dir
  subnet_id           = aws_subnet.public.id
  vpc_security_group_ids = [module.sg.sg-id]

}
module "ec2Config" {
  source              = "../Modules/EC2s/EC2Config"
  author_name         = var.author_name
  instance_type       = var.instance_type
  private_key_path    = var.private_key_path
  availability_zone   = var.ec2_avail_zone
  sg_name             = module.sg.out-sg-name
  public_ip           = module.eip.out_eip_ip
  main_directory      = var.main_dir
  subnet_id           = aws_subnet.public.id
  vpc_security_group_ids = [module.sg.sg-id]
}

module "ec2Gateway" {
  source              = "../Modules/EC2s/EC2Gateway"
  author_name         = var.author_name
  instance_type       = var.instance_type
  private_key_path    = var.private_key_path
  availability_zone   = var.ec2_avail_zone
  sg_name             = module.sg.out-sg-name
  public_ip           = module.eip.out_eip_ip
  main_directory      = var.main_dir
  subnet_id           =aws_subnet.public.id
  vpc_security_group_ids = [module.sg.sg-id]
}
module "ec2Registry" {
  source              = "../Modules/EC2s/EC2Registry"
  author_name         = var.author_name
  instance_type       = var.instance_type
  private_key_path    = var.private_key_path
  availability_zone   = var.ec2_avail_zone
  sg_name             = module.sg.out-sg-name
  public_ip           = module.eip.out_eip_ip
  main_directory      = var.main_dir
  subnet_id           =aws_subnet.public.id
  vpc_security_group_ids = [module.sg.sg-id]
}

module "sg" {
  source   = "../Modules/SG"
  tag_name = var.author_name
  vpc_id = aws_vpc.default.id
}

module "eip" {
  source      = "../Modules/EIP"
  author_name = var.author_name
}

resource "aws_eip_association" "eip_association" {
  allocation_id = module.eip.out_eip_id
  instance_id   = module.ec2Config.out-ec2-id
}


resource "aws_vpc" "default" {
  cidr_block           = "10.0.0.0/16"
  enable_dns_hostnames = true
}

resource "aws_internet_gateway" "gw" {
  vpc_id = aws_vpc.default.id

}

resource "aws_subnet" "public" {
  vpc_id = aws_vpc.default.id
  cidr_block = "10.0.16.0/24"
  availability_zone = var.ec2_avail_zone
  map_public_ip_on_launch = true
  
  tags = {
    Name = "subnet-public"
  }
}

resource "aws_vpc_endpoint" "ec2" {
  vpc_id       = aws_vpc.default.id
  vpc_endpoint_type = "Interface"
  service_name = "com.amazonaws.us-east-2.ec2"
}

resource "aws_vpc_endpoint_security_group_association" "sg_ec2s" {
  vpc_endpoint_id   = aws_vpc_endpoint.ec2.id
  security_group_id = module.sg.sg-id
}

resource "aws_route_table" "public" {
  vpc_id = aws_vpc.default.id

  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.gw.id
  }

  tags = {
    Name = "public-rt"
  }
} 

resource "aws_route_table_association" "public" {
  subnet_id = aws_subnet.public.id
  route_table_id = aws_route_table.public.id
}



