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
6. You MUST select the min width && max width for the given plan (and must find out the best values - by trial and error. First try an interval you think it's good, see how many walls are found, then enlarge or shrink the interval etc.). 
6. Start Processing. You will see the progress bar updating, the text details appearing and the image changing.

Obs:
1. The default values on screen are loaded for the 'lift-test' (template folder: 'templates2', min-width: 15, max-width: 25)
2. For the 'test2.png' (the faculty building plan): template folder - 'templates1'. Best results obtained (althought there are gaps between walls) with: min-width: 6, max-width: 100 (huge gaps)
3. Sometimes, the image displayed in the form does not show all the detected lines (because the image is huge, the lines are thin etc.) But keep calm- you can see the images in the project folder. They are numerotated and the one containing the lines is the last one (you can see on the other ones, if interested, the process of finding & post-processing).

-------------------------------------
PART 3 - TESTING
-------------------------------------
One should run multiple times the applicationm, configuring it as said above.
In order to test an application, one should get a valid building plan, then get from it each possible object template (eg. doors, windows, stairs, elevators, etc) with the naming conventions.
Then one should run the application (cu min-width si max-width setate asa dupa ochi, gen daca la ala era 6 - 100, la asta ar fi cam...) and see the results. If the templates are cropped correctly, the template identifying should work well. Then, if the wall detection does not detect all walls, make the `Only walls` checkbox checked, change the min-width and max-width and then run again. etc.

One should make an word file, containing chapters for each of the test runned on each building. Two tests are already in the application folder, so one should only run them and write down the result.
The test for each building should contain:
		Title
		The image of the original building plan (with text saying it is the original building)
		The image obtained after the template matching (it is always in '_output/template_output.png').
				Aici trebe scris ce obiecte s-au identificat, ce obiecte nu s-au identificat (de ex. daca da gres sa detecteze nu stiu ce lift etc).
		The image obtained after the wall detection (alwaayw the last one in the project folder)
				Aici trebe scris un procent cam cat la suta din linii au fost detectate si cu ce parametri (min-width, max-width). De asemenea aici poate fi rulat de mai multe ori cu diversi parametri si puse printscreenuri, pentru a vedea de ex. un rezultat foarte prost si unul foarte bun.
				De asemenea poate fi precizat cat la suta din linii sunt deconectate etc.
		Conclusions (un procent de corectitudine.)

Testing - Groza && Dorin
		Editati si voi un document word in care sa scrieti:
				- cum functioneaza features2d (pe scurt) si de ce poate da fail uneori
				- bug-urile pe care le-ati intampinat pe parcurs si cum le-ati rezolvat (sau ce n-ati rezolvat)
				- ar fi bune si niste printscreeenuri la aceste buguri (de ex. pentru atunci cand template-ul e prea mic, sau doar black & white)
Eu - o sa fac si eu ceva de genul, pentru linii.
Amin.
