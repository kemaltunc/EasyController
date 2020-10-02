# Android-Fragment-Controller

<h4>Example</h4>

<h6>Create Controller class</h6>

class MainController : BaseController()

<h6>Bind Controller</h6>
		
	val navigator = Navigator.bind<MainController>(this, R.id.container, SplashFragment()) {
		history = false
 	}
     
     
 <h6>Navigate Fragment</h6>
  
	val navigator =	Navigator.find<MainController>(HomeFragment()) {

	}.navigate()    
	
 <h6>Create BottomNavigationView</h6>
 	 
    root.fragment_main_navigation.create<ChildController>(
                listOf(
                    firstFragment, secondFragment, thirdFragment, fourthFragment, fiveFragment
                ),
                navigator
            )
			
<h2>Add RecyclerviewDSL to your project</h2>
     
<h6>Via Gradle</h6>
        
    allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

    implementation 'com.github.kemaltunc:RecyclerViewDSL:1.1.0' 
