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
                sh '/opt/apache-maven-3.6.3/bin/mvn sonar:sonar -Dsonar.projectKey=studentapp-ui -Dsonar.host.url=http://3.110.214.184:9000 -Dsonar.login=75df1c9e34a0449ff40447fe08bd4b8424396605'
                echo 'Testing done'
            }
        } 

        stage('Deploy to EC2') {
            steps {
                script {
                    // Copy built artifacts to EC2 instance
                    sshagent(['ubuntuid']) {
                        sh 'scp -o StrictHostKeyChecking=no -v /var/lib/jenkins/workspace/student-app/target/studentapp-2.2-SNAPSHOT.war root@ip-172-31-13-217:/opt/apache-tomcat-9.0.87/webapps'
                    }
                }
            }
        }


    }
}

