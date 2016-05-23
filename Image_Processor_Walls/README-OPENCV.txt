http://docs.opencv.org/3.0-last-rst/doc/tutorials/introduction/java_eclipse/java_eclipse.html#java-eclipse
- install tutorial

Things to do this project work (freaking simple):
1. Create the user library opencv-310 (as in opencv tutorial, with path to the native library)
2. Create the user library opencv-249 (as in opencv tutorial, with path to the native library)
3. Add FIRST to the build path the opencv-310 library (because it contains more, update claseses, as well as LineDetector which is not contained in the opencv-249 so cannot be overriden.
4. Add SECONDly to the build path the opencv-249. The project will have no errors (because of what is said in the paranthesis above  - the LSD is visible because opencv249 does not override that class, the feature2d is visible because opencv310 does not contain that class).
5. In the code, there is some magin with the System Loads. I found out what Core.NATIVE_LIBRARY_NAME was for each of the libraries. I first loaded the '249' because we first start the Features2d detection.
	Java has the files, now when it searches for the native code for the features2d, it finds it, it does the job.
	Next, when Java is about to use fillConvexPoly from ImgProc, we call the '310' library (because, from now on, we only use this one, the next sentences does not interfere amongst the two libraries.
	(and using Core.fillConvexPoly does not work, because it interferes).
6. Done. Magic.

--------------------------------
PART 2 - HOW IT WORKS
--------------------------------

1. Open the App.
2. Load the image (now it finally starts in the current folder)
3. Set the template folder name. The templates folder should be added in the '_input' folder (see, there are already two folders there - 'template1' for the school plan detection and 'templates2' for the lift-test.png file).
4. For each building plan you want to detect you should create a separate template folder, and put there all the objects you want to detect in that plan (objects which one should crop from the original building plan).
5. In this template folder, each template should have suggestive names, because they are now automatically identified (the program scans the entire given folder and identifies and removes all the objects given there). So, a stair template should have the word 'stair' (there can also be multiple stair configurations - see 'templates1' for proof).
6. Start Processing. You will see the progress bar updating, the text details appearing and the image changing.