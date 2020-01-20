pipeline{

	agent any

	tools {
		maven 'Maven 3.3.9'
		jdk 'JDK 8'
	}

	stages {
        stage('Performance') {
            steps {
                sh 'mvn gatling:test -Dgatling.simulationClass=gatling.DemoSimulation'
            }
        }

        stage('Visualizing data') {
            steps {
                 gatlingArchive()
            }
        }
    }
}