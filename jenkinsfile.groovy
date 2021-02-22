  
node{
    stage("checkout"){
        checkout scm
    }
    stage("Build"){
       
     sh "mvn clean compile package"
    }
   
    stage("deploy"){
      timeout(time: 15, unit: "MINUTES") {    input message: 'Do you want to approve the deploy in production?', ok: 'Yes'}
   sshagent(['3e61a85b-994b-47e6-9c94-78e83f54ba82']) {
   sh "scp -o StrictHostKeyChecking=no /var/lib/jenkins/workspace/webapp/target/ root@192.168.56.23:/opt/tomcat/webapps"
}

    }
}
    

