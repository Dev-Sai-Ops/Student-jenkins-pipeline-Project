pipeline {
    agent any

    stages {

        stage('Pull') {
            steps {
                git 'https://github.com/chetansomkuwar254/studentapp.ui.git'
                echo 'Hello World'
            }
        }

        stage('Build') {
            steps {
                sh '/opt/apache-maven-3.6.3/bin/mvn clean package'
                echo 'Hello World'
            }
        }

        stage('Test') {
            steps {
                sh '/opt/apache-maven-3.6.3/bin/mvn sonar:sonar -Dsonar.projectKey=studentapp-ui -Dsonar.host.url=http://65.0.168.94:9000 -Dsonar.login=64a4a3e55c70e53044e0f9f42b2739d6d47933dd'
                echo 'Testing done'
            }
        } 

        stage('Deploy to EC2') {
            steps {
                script {
                    sshagent(['ubuntuid']) {
                        sh 'scp /var/lib/jenkins/workspace/student-app/target/*.war ubuntu@ip-172-31-13-217:/opt/apache-tomcat-9.0.87/webapps'
                    }
                }
            }
        }


    }
}
