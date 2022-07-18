System.out.println("Executing .gitignore hack")
file = new File( request.getOutputDirectory(), request.getArtifactId()+"/.gitignore.tmpl" );
def gitIgnorefile = new File( request.getOutputDirectory(), request.getArtifactId()+"/.gitignore" );
file.renameTo(gitIgnorefile)

System.out.println("Initializing git:\n"+request.getVersion())
def initProcess = new ProcessBuilder("git","init").directory(new File(request.getArtifactId())).start()
initProcess.waitForProcessOutput(System.out, System.err)
def initialAddProcess = new ProcessBuilder("git","add",".").directory(new File(request.getArtifactId())).start()
initialAddProcess.waitForProcessOutput(System.out, System.err)
def initialCommitProcess = new ProcessBuilder("git","commit","-m","chore: initial commit").directory(new File(request.getArtifactId())).start()
initialCommitProcess.waitForProcessOutput(System.out, System.err)
def tagProcess = new ProcessBuilder("git","tag", request.getVersion()).directory(new File(request.getArtifactId())).start()
tagProcess.waitForProcessOutput(System.out, System.err)

def activeProfiles = request.getProjectBuildingRequest().getActiveProfileIds()
def isWindows = false
def isUnix = false
for (i=0;i<activeProfiles.size();i++) {
    if(activeProfiles[i].equals("windows-local-build"))
        isWindows=true
    if(activeProfiles[i].equals("unix-local-build"))
        isUnix=true
}
def mavenInstallProcess
if(isWindows && isUnix) {
    System.out.println("BOTH windows and UNIX profiles? Check your config...")
    System.exit(1)
}
else if(isWindows) {
    System.out.println("Executing 'mvn install -DskipTests=true -P e2e-build,windows-local-build' in directory "+request.getArtifactId())
    mavenInstallProcess = new ProcessBuilder("mvn.cmd","install","-DskipTests=true","-P e2e-build,windows-local-build").directory(new File(request.getArtifactId())).start()
}
else if(isUnix) {
    System.out.println("Executing 'mvn install -DskipTests=true -P e2e-build,unix-local-build' in directory "+request.getArtifactId())
    mavenInstallProcess = new ProcessBuilder("mvn","install","-DskipTests=true","-P e2e-build,unix-local-build").directory(new File(request.getArtifactId())).start()
}
else {
    System.out.println("NEITHER windows NOR UNIX profiles? Check your config...")
    System.exit(1)
}
mavenInstallProcess.waitForProcessOutput(System.out, System.err)

/*
System.out.println("Executing 'mvn clean' in directory "+request.getArtifactId())
def mavenCleanProcess
if(isWindows && isUnix) {
    System.out.println("BOTH windows and UNIX profiles? Check your config...")
    System.exit(1)
}
else if(isWindows) {
    System.out.println("Executing 'mvn package -DskipTests=true' in directory "+request.getArtifactId())
    mavenCleanProcess = new ProcessBuilder("mvn.cmd","clean").directory(new File(request.getArtifactId())).start()
}
else if(isUnix) {
    System.out.println("Executing 'mvn package -DskipTests=true' in directory "+request.getArtifactId())
    mavenCleanProcess = new ProcessBuilder("mvn","clean").directory(new File(request.getArtifactId())).start()
}
else {
    System.out.println("NEITHER windows NOR UNIX profiles? Check your config...")
    System.exit(1)
}
mavenCleanProcess.waitForProcessOutput(System.out, System.err)
*/

System.out.println("Adding to git")
def addProcess = new ProcessBuilder("git","add",".").directory(new File(request.getArtifactId())).start()
addProcess.waitForProcessOutput(System.out, System.err)
def commitProcess = new ProcessBuilder("git","commit","-m","chore: complete first-time setup").directory(new File(request.getArtifactId())).start()
commitProcess.waitForProcessOutput(System.out, System.err)

System.out.println("Done!")