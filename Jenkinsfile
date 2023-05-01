pipeline {
    agent any

    tools {
        maven "3.8.6"
    }

parameters {
gitParameter branchFilter: 'origin/(.*)', defaultValue: 'master', name: 'BRANCH', type: 'PT_BRANCH'
 string (defaultValue: 'ShareLane', description: 'Enter project name', name: 'projectName')
 choice (choices: ['smoke.xml', 'regression.xml', 'negative.xml', 'api.xml'], description: 'Choose xml file to run tests', name: 'XMLfile')
}

    stages {
        stage('Build') {
            steps {
                git branch: "${params.BRANCH}", url: 'https://github.com/tatyana-akulich/DiplomaQase.git'
                bat "mvn clean test -DsuiteXmlFile=src/test/resources/${params.XMLfile}"
            }
        }
        stage('reports') {
            steps {
                script {
                    allure([
                     includeProperties: false,
                     jdk: '',
                     properties: [],
                     reportBuildPolicy: 'ALWAYS',
                     results: [[path: 'target/allure-results']]
                     ])
                }
            }
        }
    }
}
