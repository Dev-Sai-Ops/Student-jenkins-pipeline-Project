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
                sh '/opt/apache-maven-3.6.3/bin/mvn sonar:sonar -Dsonar.projectKey=studentapp-ui -Dsonar.host.url=http://52.66.207.203:9000 -Dsonar.login=75df1c9e34a0449ff40447fe08bd4b8424396605'
                echo 'Testing done'
            }
        } 

        stage('Deploy to EC2') {
            steps {
                script {
                    // Copy built artifacts to EC2 instance
                    sshagent(['ubuntuid']) {
                        sh 'scp /var/lib/jenkins/workspace/student-app/target/*.war ubuntu@ip-172-31-13-217:/opt/apache-tomcat-9.0.87/webapps'
                    }
                }
            }
        }


    }
}

