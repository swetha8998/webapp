  
node{
    stage("checkout"){
        checkout scm
    }
    stage("Build"){
       
     sh "mvn clean compile package"
    }
   
    stage("deploy"){
        sshagent(['']) {
     sh "scp /var/lib/jenkins/workspace/heloapp/target/maven-0.0.1-SNAPSHOT.war root@192.168.56.23://opt/tomcat/webapps"
}
    }
    
}
