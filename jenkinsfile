pipeline {
    agent any

    tools {
        maven 'maven'

    }

    stages {
        

        stage('Build Spring Boot Maven') {
            steps {
                git branch: 'master', url: 'https://github.com/brahgithubsal/SPRING-BOOT.git'
            }
        }
        stage('Backend File System Scan') {
            steps {
                sh "trivy fs --format table -o trivy-fs-report.html ."
            }
        }

        stage("SonarQube Analysis Maven"){
           steps {
               script {
                    withSonarQubeEnv(credentialsId: 'jenkins-sonarqube-token') { 
                        sh "mvn sonar:sonar"
                    }
               }   
           }
       }
        stage('Quality Gate Maven') {
            steps {
                script {
                  waitForQualityGate abortPipeline: false, credentialsId: 'jenkins-sonarqube-token' 
                }
            }
        }


        stage('Build Spring Boot Docker image') {
            steps {
                script {
                    sh 'docker build -t azizbk9/spring-image .'
                }
            }
        }
        stage('Scan Spring Boot Docker image') {
            steps {
                sh "trivy image --format table -o trivy-image-report.html azizbk9/spring-image "
            }
        }


        stage('Push Spring Boot image to Hub') {
            steps {
                script {
                    withCredentials([string(credentialsId: 'dockerhub-pwd', variable: 'dockerhubpwd')]) {
                        sh "echo \$dockerhubpwd | docker login -u azizbk9 --password-stdin"
                        sh 'docker push azizbk9/spring-image'
                    }
                }
            }
        }
    }
}
