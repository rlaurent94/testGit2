pipeline {
	agent any
	
	tools {
		maven 'maven-3.5.2'
	}
	
	stages {
		stage("build") {
			steps {
				maven('maven') {
					sh 'mvn clean compile'
				}
			}
		}
	}
}
