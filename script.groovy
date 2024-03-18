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

        stage('Deploy') {
            steps {
                echo 'Deploy done'
            }
        }

    }
}
