  
node{
    stage("checkout"){
        checkout scm
    }
    stage("Build"){
       
     sh "mvn clean compile package"
    }
   
    stage("deploy"){
      timeout(time: 15, unit: "MINUTES") {    input message: 'Do you want to approve the deploy in production?', ok: 'Yes'}
         sh "scp /var/lib/jenkins/workspace/heloapp/target/maven-0.0.1-SNAPSHOT.war root@192.168.56.23://opt/tomcat/webapps"

    }
    
}
