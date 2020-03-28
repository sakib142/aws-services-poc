provider "aws" {
  access_key                  = "mock_access_key"
  secret_key                  = "mock_secret_key"
  region                      = "us-east-1"
  s3_force_path_style         = true
  skip_credentials_validation = true
  skip_metadata_api_check     = true
  skip_requesting_account_id  = true

  endpoints {
    apigateway     = "http://10.8.20.136:4567"
    cloudformation = "http://10.8.20.136:4581"
    cloudwatch     = "http://10.8.20.136:4582"
    dynamodb       = "http://10.8.20.136:4569"
    es             = "http://10.8.20.136:4578"
    firehose       = "http://10.8.20.136:4573"
    iam            = "http://10.8.20.136:4593"
    kinesis        = "http://10.8.20.136:4568"
    lambda         = "http://10.8.20.136:4574"
    redshift       = "http://10.8.20.136:4577"
    route53        = "http://10.8.20.136:4580"
    s3             = "http://10.8.20.136:4572"
    secretsmanager = "http://10.8.20.136:4584"
    ses            = "http://10.8.20.136:4579"
    sns            = "http://10.8.20.136:4575"
    sqs            = "http://10.8.20.136:4576"
    ssm            = "http://10.8.20.136:4583"
    stepfunctions  = "http://10.8.20.136:4585"
    sts            = "http://10.8.20.136:4592"
  }
}
resource "aws_s3_bucket" "EmsBucket" {
  bucket = "ems-bucket"
  acl = "private"
}
resource "aws_api_gateway_rest_api" "EMSDemoAPI" {
  name        = "ems-api-gateway"
  description = "This is my API for demonstration purposes"
}
resource "aws_kinesis_stream" "EmsStream" {
  name             = "ems-kinesis-test"
  shard_count      = 1
  retention_period = 48

  shard_level_metrics = [
    "IncomingBytes",
    "OutgoingBytes",
  ]

  tags = {
    Environment = "test"
  }
}
resource "aws_dynamodb_table" "EmsDynamoDB" {
  name           = "ems-market-depth"
  billing_mode   = "PROVISIONED"
  read_capacity  = 20
  write_capacity = 20
  hash_key       = "UserId"
  range_key      = "marketTitle"

  attribute {
    name = "UserId"
    type = "S"
  }

  attribute {
    name = "marketTitle"
    type = "S"
  }

  attribute {
    name = "TopScore"
    type = "N"
  }

  ttl {
    attribute_name = "TimeToExist"
    enabled        = false
  }

  global_secondary_index {
    name               = "MarketTitleIndex"
    hash_key           = "marketTitle"
    range_key          = "TopScore"
    write_capacity     = 10
    read_capacity      = 10
    projection_type    = "INCLUDE"
    non_key_attributes = ["UserId"]
  }

  tags = {
    Environment = "test"
  }
}