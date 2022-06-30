variable "instance_type" {
  type    = string
  default = "t2.micro"
}

variable "author_name" {
  type = string
}

variable "private_key_path" {
  type = string
}

variable "sg_name" {
  type = string
}

variable "availability_zone" {
  type = string
}

variable "public_ip" {
  type = string
}

variable "main_directory" {
  type = string
}

variable "subnet_id" {
  type    = string
}
variable "vpc_security_group_ids" {
  type = list(string)
}