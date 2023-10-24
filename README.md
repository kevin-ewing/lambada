# Lambada

Create more passionate AWS Lambda functions using clojure. The library works to create a seamless interface between Clojure and AWS Lambda, facilitating the development of serverless applications. This document outlines building, and deploying a Clojure-based AWS Lambda function.

Current version:

[![Clojars Project](https://img.shields.io/clojars/v/org.clojars.kevin-ewing/lambada.svg)](https://clojars.org/org.clojars.kevin-ewing/lambada)

## Usage

### Defining the Lambda Handler

The Lambda handler is the function AWS Lambda invokes when your function is executed. In Clojure, we can define a Lambda handler using the `deflambdafn` macro provided by the `lambada.core` namespace.

Here is a step-by-step guide to set up your Lambda handler:

1. **Namespace Declaration:** Start by declaring your namespace and importing the required libraries.

```clojure
(ns example.lambda
  (:require [lambada.core :refer [deflambdafn]]))
```

2. **Defining the Function Logic:** Create a function that contains the business logic of your Lambda function. This function will take an event map as its parameter and return a map representing the Lambda response.

```clojure
(defn do-something
  [event-map]
  {:status 200
   :body "Hello, World!"})
```

3. **Defining the Lambda Handler:** Use the deflambdafn macro to define your Lambda handler. This macro takes three parameters: the input stream, the output stream, and the Lambda context.

```clojure
(deflambdafn example.lambda.MyLambdaFn
  [in out ctx]
  (let [event (json/read (io/reader in))
        response (do-something event)]
    (with-open [w (io/writer out)]
      (json/write response w))))
```

### Building the `.jar`

Once your Lambda handler is defined, the next step is to build the project into a `.jar` file, which can be uploaded to AWS Lambda. This `.jar` file will include all the necessary dependencies and compiled classes.

#### Simple Uberjar Deployment
The simplest way to deploy is to create an uberjar using tools like Leiningen or Boot. These tools will bundle your application and its dependencies into a single, executable JAR file.

For example, if you are using Leiningen, you can add the following to your project.clj file:

```clojure
:uberjar-name "my-lambda-project.jar"
```
And then run:

```sh
$ lein uberjar
```
This command will generate the my-lambda-project.jar file in the target directory.

When this namespace is AOT compiled, it will generate a class called
`example.lambda.MyLambdaFn` that implements the AWS Lambda
[`RequestStreamHandler`](http://docs.aws.amazon.com/lambda/latest/dg/java-handler-using-predefined-interfaces.html)
interface using the args and body provided.

### Deploying to AWS

Once you have your uberjar ready, you can deploy your Clojure Lambda function to AWS.

#### AWS CLI

The AWS Command Line Interface (CLI) is a powerful tool to manage AWS services. You can use it to deploy your Lambda function with the following command:

```sh
$ aws lambda create-function \
    --region us-east-1 \
    --function-name my-lambda \
    --zip-file fileb://$(pwd)/target/my-lambda-project.jar \
    --role arn:aws:iam::YOUR-AWS-ACCOUNT-ID:role/lambda_basic_execution \
    --handler example.lambda.MyLambdaFn \
    --runtime java11 \
    --timeout 15 \
    --memory-size 512
```

#### Cloudformation

AWS CloudFormation provides a way to model and provision AWS infrastructure resources. You can define your Lambda function and its associated resources in a YAML template and deploy it using the AWS Management Console, AWS CLI, or AWS SDKs.

Below is a sample CloudFormation template to deploy your Lambda function:

```yaml
AWSTemplateFormatVersion: '2010-09-09'
Resources:
  MyLambdaFunction:
    Type: AWS::Lambda::Function
    Properties:
      Handler: example.lambda.MyLambdaFn
      Role: arn:aws:iam::YOUR-AWS-ACCOUNT-ID:role/lambda_basic_execution
      Code:
        S3Bucket: <Your S3 Bucket Name>
        S3Key: <S3 Key to your my-lambda-project.jar file>
      Runtime: java11
      MemorySize: 512
      Timeout: 15
```

It can also be written in `.json`.

```json
{
  "AWSTemplateFormatVersion": "2010-09-09",
  "Resources": {
    "MyLambdaFunction": {
      "Type": "AWS::Lambda::Function",
      "Properties": {
        "Handler": "example.lambda.MyLambdaFn",
        "Role": "arn:aws:iam::YOUR-AWS-ACCOUNT-ID:role/lambda_basic_execution",
        "Code": {
          "S3Bucket": "<Your S3 Bucket Name>",
          "S3Key": "<S3 Key to your my-lambda-project.jar file>"
        },
        "Runtime": "java11",
        "MemorySize": 512,
        "Timeout": 15
      }
    }
  }
}
```

See [here](https://github.com/kevin-ewing/lambada/tree/master/example) for an example project.

## License

Copyright © 2015 Ragnar Dahlen
Copyright © 2023 Kevin Ewing

Distributed under the Eclipse Public License, the same as Clojure.
