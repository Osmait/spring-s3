# Configuración del proveedor de Terraform para AWS
provider "aws" {
  region                      = "us-east-1" # Puedes cambiar la región si lo deseas
  access_key                  = "locastack"      # Aquí pon tu clave de acceso de AWS
  secret_key                  = "localstack"      # Aquí pon tu clave secreta de AWS
  skip_credentials_validation = true
  skip_metadata_api_check     = true
  skip_requesting_account_id  = true
  s3_use_path_style           = true
  endpoints {
    # Configuración para usar LocalStack
    s3        = "https://localhost.localstack.cloud:4566"
    s3control = "https://localhost.localstack.cloud:4566"
  }
}

# Recurso de ejemplo para crear un bucket de S3 en LocalStack
resource "aws_s3_bucket" "example_bucket" {
  bucket = "saul-burgos-s3-bucket"
}