resource "aws_instance" "ec2" {
  ami                    = data.aws_ami.ami-ubuntu-bionic.id
  instance_type          = var.instance_type
  /* security_groups        = ["${var.sg_name}"] */
  availability_zone      = var.availability_zone
  key_name               = var.author_name
  subnet_id              = var.subnet_id
  vpc_security_group_ids = var.vpc_security_group_ids
  associate_public_ip_address = true
  private_ip = "10.0.16.14"
  tags = {
    Name : "ec2-${var.author_name}-registry"
  }

  provisioner "local-exec" {
#    command = "echo IP : ${var.public_ip}, ID: ${aws_instance.ec2.id}, Zone: ${aws_instance.ec2.availability_zone} > ${var.main_directory}/ip.host"
     command = "echo ${var.public_ip} > ${var.main_directory}/ip.host"
  }
  
  provisioner "local-exec" {
    command = "echo '[webserver]\n${self.public_ip}' > ${var.main_directory}/hostsRegistry.ini"

  }
  
  provisioner "remote-exec" {
    connection {
      type        = "ssh"
      user        = "ubuntu"
      private_key = file(var.private_key_path)
      host        = self.public_ip
    }

    inline = [
      "sudo apt update -y"
    ]
  }
  
  provisioner "local-exec" {
    command = "ANSIBLE_HOST_KEY_CHECKING=false ansible-playbook -u ubuntu -b -i ${var.main_directory}/hostsRegistry.ini --private-key ${var.private_key_path} ${var.main_directory}playbooks/playbookRegistry.yml"
  }
}

data "aws_ami" "ami-ubuntu-bionic" {
  most_recent = true
  owners      = ["099720109477"]
  tags = {
    Name = "${var.author_name}-ec2-ami-t2-ubuntu-bionic"
  }
  filter {
    name   = "name"
    values = ["ubuntu/images/hvm-ssd/ubuntu-bionic-18.04-amd64-server*"]
  }
}


