pipeline {

  agent any

  tools {
    jdk 'JDK8_Centos'
    gradle 'Gradle4.5_Centos'
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

    stage('Clean, Compile & Unit Tests') {
        steps{
          echo "------------>Unit Tests<------------"
            sh 'build --b ./build.gradle clean'
            sh 'build --b ./build.gradle compileJava'
            sh 'build --b ./build.gradle test'
        }
      }

    stage('Static Code Analysis') {
          steps{
            echo '------------>Análisis de código estático<------------'
            withSonarQubeEnv('Sonar') {
    sh "${tool name: 'SonarScanner', type:'hudson.plugins.sonar.SonarRunnerInstallation'}/bin/sonar-scanner -Dproject.settings=sonar-project.properties"
            }
          }
        }

    stage('Build') {
          steps {
            echo "------------>Build<------------"
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
