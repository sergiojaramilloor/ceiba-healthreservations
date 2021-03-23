pipeline {

  agent {
      label 'Slave_Induccion'
    }

  options {
        buildDiscarder(logRotator(numToKeepStr: '3'))
     	disableConcurrentBuilds()
      }

  tools {
    jdk 'JDK8_Centos'
    gradle 'Gradle5.6_Centos'
  }

  stages {

    stage('Checkout') {
        steps{
          echo "------------>Checkout<------------"
            checkout([
                $class: 'GitSCM',
                branches: [[name: '*/main']],
                gitTools: 'Default',
                userRemoteConfigs:[[
                    credentialsId: 'GitHub_sergiojaramilloor',
                    url: 'https://github.com/sergiojaramilloor/ceiba-healthreservations.git'
                ]]
            ])
        }
      }

    stage('Clean') {
        steps{
          echo "------------>Clean Project<------------"
            sh 'gradle --b ./healthCareBackend/java-arquitectura-hexagonal/microservicio/build.gradle clean compileJava'
        }
      }

    stage('Compile & Unit Tests') {
        steps{
          echo "------------>Unit Tests<------------"
            sh 'gradle --b ./healthCareBackend/java-arquitectura-hexagonal/microservicio/build.gradle test'
        }
      }

    stage('Static Code Analysis') {
          steps{
            echo '------------>Análisis de código estático<------------'
            withSonarQubeEnv('Sonar') {
                sh "${tool name: 'SonarScanner', type:'hudson.plugins.sonar.SonarRunnerInstallation'}/bin/sonar-scanner -Dsonar.projectKey=sergio.jaramillo_healthCareBackend -Dsonar.projectName=ADNCeiba-healthCareBackend -Dproject.settings=sonar-project.properties"
            }
          }
        }

    stage('Build') {
          steps {
            echo "------------>Build<------------"
            sh 'gradle --b ./healthCareBackend/java-arquitectura-hexagonal/microservicio/build.gradle build -x test'
          }
        }

  }

  post {
    failure {
        echo 'This will run only if failed'
            mail (to: 'sergio.jaramillo@ceiba.com.co',subject: "Failed Pipeline:${currentBuild.fullDisplayName}",body: "Something is wrong with ${env.BUILD_URL}")
    }
    success {
    	echo 'This will run only if successful'
    }
  }

}
