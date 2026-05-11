pipeline {
  agent any

  tools {
    jdk 'jdk21'
    allure 'Allure'
  }

  parameters {
    string(name: 'TAGS',       defaultValue: 'regress',         description: 'JUnit5 tag expression (smoke, regress, frontend, backend, etc.)')
    string(name: 'ENV_CONFIG', defaultValue: '/ci.properties',  description: 'Properties file from resources')
  }

  options { timestamps() }

  stages {
    stage('Checkout') { steps { checkout scm } }

    stage('Test') {
      steps {
        sh """
          chmod +x ./gradlew
          ./gradlew clean test \
            -Denv_config=${params.ENV_CONFIG} \
            -DTAGS=${params.TAGS} || true
        """
      }
    }

    stage('Allure report') {
      steps {
        sh 'allure generate build/allure-results -o build/allure-report --clean'
      }
    }

    stage('Push to InfluxDB') {
      when { expression { fileExists('build/allure-report/export/influxDbData.txt') } }
      steps {
        sh '''
          curl -i -XPOST "http://influxdb:8086/write?db=allure" \
               --data-binary @build/allure-report/export/influxDbData.txt
        '''
      }
    }
  }

  post {
    always {
      allure([
        includeProperties: false,
        results: [[ path: 'build/allure-results' ]]
      ])
    }
  }
}