# Android TODO
Example Android Todo application

## Setup
1.  Install the [Android SDK Bundle](http://developer.android.com/sdk/index.html "Android SDK Bundle")
2.  Create a variable named $ANDROID_HOME that points to that directory
3.  Add $ANDROID_HOME/tools and $ANDROID_HOME/platform-tools to the path
4.  Unpack the ZIP file (named adt-bundle-<os_platform>.zip) and save it to an appropriate location, such as a "Development" directory in your home directory.
5.  cd to where you want your code to live
6.  git clone git@github.com:jkeam/android_todo.git

### Eclipse
Do this if you want to use eclipse.

1.  Open the adt-bundle-<os_platform>/eclipse/ directory and launch eclipse.
2.  File -> Import -> Android -> Existing Android Code Into Workspace
3.  Window -> Android Virtual Device Manager 
4.  Click New... and fill out:
  *  AVD Name: Nexus_4
  *  Device: Nexus 4
  *  Target: Android 4.2.2
  *  Keyboard: click
  *  Skin: click
  *  Front Camera: None
  *  Back Camera: None
  *  RAM: 1907
  *  VM Heap: 64
  *  Internal Storage: 200MiB
  *  Click OK
5. In eclipse, Run -> Run, choose Launch a new Android Virtual Device and choose Nexus_4 that you just created and click OK
6. See your app run

### Command Line
This is quickly becoming my preferred way of developing.  There is also Android-Studio is built on IntelliJ and looks promising but at this point it's Early Access Preview only.

#### Install Emulator
There is no need to do this if you already did it in the Eclipse instructions.  But if not, go ahead and do this.

1.  Run the following command in the terminal

    android

2.  Go to Tools -> Manage AVDs... 
3.  Click New... and fill out:
  *  AVD Name: Nexus_4
  *  Device: Nexus 4
  *  Target: Android 4.2.2
  *  Keyboard: click
  *  Skin: click
  *  Front Camera: None
  *  Back Camera: None
  *  RAM: 1907
  *  VM Heap: 64
  *  Internal Storage: 200MiB
  *  Click OK
4.  Close the android tool down completely, you won't need it anymore (oh by the way, use this tool to update your android sdk if it goes out of date)
5.  To run the emulator, just type the following in the terminal (note this isn't headless.  also note that this takes a while to boot.) 

    emulator -avd Nexus_4

#### Building
To build and install the code (first make sure the emulator is running then)

    gradle installDebug

To uninstall 

    gradle uninstallDebug

#### Testing
To build, install, and run tests (first make sure the emulator is running then)

    gradle connectedInstrumentTest

To view the reports open _./build/reports/instrumentedTests/connected/index.html_

#### Notes
To see the entire list of available tasks run

    gradle tasks 

or

    gradle tasks --all

