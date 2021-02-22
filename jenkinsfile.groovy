  
node{
    stage("checkout"){
        checkout scm
    }
    stage("Build"){
       
     sh "mvn clean compile package"
    }
   
    stage("deploy"){
      timeout(time: 15, unit: "MINUTES") {    input message: 'Do you want to approve the deploy in production?', ok: 'Yes'}
    deploy adapters: [tomcat8(credentialsId: 'tomcat', path: '', url: 'http://192.168.56.23:8090/')], contextPath: '/var/lib/jenkins/workspace/webapp/target', war: '**/*.war"'
    }
}
    

