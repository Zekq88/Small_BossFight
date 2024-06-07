# Final Project

## Environment & Tools
Windows 10, IntelliJ IDEA 2023.2.4, Git v. 2.30.0.windows.2

## Purpose
This project will be a small section of a dungeon crawler game where our hero will fight the last boss.

#### Concrete Goals:
This project will aim for an E grade and the requirements were Event Driven Application, MVC Pattern, using Graphical Interface,
minimum of two concurrent processes, minimum of three unique Swing components, minimum of one Swing layouts,
minimum of two design patterns and additionally Stream APIs were implemented but not mandatory for the grade requirement.
The game will be created using MVC, Event Dispatch Thread, Consumer & Producer, and Observer pattern.

## Procedures
The Game itself was implemented first and the different patterns were applied afterwards.
The requirements were implemented and added like a puzzle in order to make the game work.

* The first requirement is ``Event Driven Application, using Graphical Interface`` and the game takes input from the user
   using both KeyListener and MouseListener to either move or attack with the Hero.
*  The second requirement is ``concurrent processes`` and has a minimum amount of two, and I choose two of them.
   One is in the class _HealthValue_ is a ``BlockingQueue<Integer>`` named _LifeSteal_ and 
   is a part of the consumer and producer pattern later discussed. Inside the _Controller_ is the other one, a ``ExecutorService`` and a ``Executors.newFixedThreadPool``
   with the name of _executor_ that adding smoothness to the game engine.
* The third requirement is ``unique Swing components`` and a minimum of three and I them all inside the _View:: setupWindow()_, A JFrame, 
    JPanel, and JMenu with a JMenuItem.
* The forth requirement is one ``unique Swing layouts `` and was implemented in the ``setupWindow()`` method
  and the Swing layout _BorderLayout.SOUTH_ contains a _Label_ and _BorderLayout.NORTH_ contains the _JMenu_.
* The fifth requirement is two ``design patterns`` and this game uses the _Observer Pattern_ and _Consumer & Producer Pattern_
  to update the user inputs and life bars.
* The sixth requirement is the ``MVC Pattern`` and has been implemented in separate classes and executed in the _Project_ class.
* The additional requirement is ``Streams API`` and is absolute required to make this game work. This Game uses Stream API
  in  these four methods ``ImportEnemyImg(), ImportEnemyDeathImg(), LoadHeroAnimations(), and View()``.

### Project.java
The class has a utility method and the``main()`` Starts the game
by creating the Event Dispatch Thread (EDT). Inside the EDT there are all the components of the MVC to execute.

### Model.java
This class is the model part of the MVC pattern and contains game logic,and the boss hitbox.
The constructor method loads the foundational method `bossHitBox()` to be executed directly.

The ``setAnimation()`` sets the different types av animation to the heroMove variable depending on the int x 
from keyListeners input.

The ``updateAnimation()`` function contains logic on what to do during the hero's movement
and keeps the animation speed smooth through the ``(animationFrame >= animationSpeed)`` and
``(animationIndex >= Constants.getHeroAnimation(heroMove))`` conditions.

The function ``bossHitBox()`` method contains the logic for the Boss health bar consist of two
if-statements ``(MovingValues.INSTANCE.getyDelta() > 280 & MovingValues.INSTANCE.getyDelta() < 360)`` and
``(MovingValues.INSTANCE.getxDelta() < 580 & MovingValues.INSTANCE.getxDelta() > 460)``.
If the Hero's position is within the _hitBox_ then the method will return _dmghit_ to true else will return false.
This boolean will be used inside the ``MouseInputs::mousePressed()`` to fulfil the condition for damaging the boss.

lastly, the `updating()` function is used to update the animation of the hero using the observer pattern inside
the controller.

### View.java
The View class extends JPanel and is the viewer part of the MVC pattern and the observer of the Observer pattern.
This constructor method inserts the background image to the stream and adds this class to the Observer pattern.

The constructor method loads the foundational methods `setupWindow(), LoadHeroAnimations(), ImportEnemyDeathImg(),
and ImportEnemyImg()` to be executed directly. 

the ``setupWindow()`` starts the JFrame with Label and a JMenu if the user decides to exit the game that way.
The ``LoadHeroAnimations()`` inserts the hero sprite sheet into the Stream.
The ``ImportEnemyDeathImg()`` inserts the boss death image into the Stream.
The `ImportEnemyImg()` inserts the boss image into the Stream.

The hero image is also inserted by function similar to the boss, but the image contains a sprite sheet which displays
different movements of the hero, attacking, running or idle.
These movements are stored inside the private variable _heroMove_ to be further used inside the function setAnimation().

The ``updateHeroMove()`` method creates and returns the 3d BufferedImage Array _heroAnimation_ which will containing
nine different animations for the Hero from the sprite sheet chopped correctly by the nestled for-loops.

This `paintComponent()` function paints and draw all the animation of the characters, the background, and the texts.
the method contains of if-statements depending on which picture of the boss to display on his current heath using the
singleton Enum ``HealthValue``, The health bars are also affected on ``HealthValue``.

### Controller.java
The class is the controller part of the MVC pattern, implements ActionListener, and the observer interface of 
the Observer pattern.
This constructor method which takes Model and View as arguments execute the EDT which starts the timer,
 actionListener, KeyListeners, MouseListener, and adds this class to the Observer patterns ArrayList.

The overridden `actionPerformed()` method notifies the Observers and adds extra stability though the threadPool of 
three threads to execute the ``update()`` method.

The override Observer method updates the animation inside the model and repaints the view.

### Subject.java
This _Subject interface_ is the subject part of the ``Observer pattern`` and contains the three methods
``void register(Observer o)``, ``void unRegister(Observer o)``, and ``void notifyObserver()``.
All of these methods gets overridden in classes that implement this interface.

### Observer.java
This _Observer interface_ is the observer part in the ``Observer pattern``.
And contains only the empty method ``default void update()``.

### KeyInput.java
This class implements the ``KeyListener`` interface and are responsible to collect inputs from the user
to determine both which animation and position of the Hero to use.

The overridden implemented method ``keyPressed()`` will use the input data from the user to
send information to the ``MovingValues`` class and ``Model::setAnimation()`` method.

The overridden implemented method ``keyReleased()`` will set the Hero animation back to _IDLE_
upon the key is released.

### MouseInputs.java
The MouseInputs class implements both the MouseListener and the Runnable interfaces who collect inputs from users'
mouse clicks and starts the consumer part of the consumer and producer pattern.

The overridden implemented method ``mouseClicked()`` takes the mouse inputs and if the
condition of a left button is clicked than the Hero's animation is set to attacking and if the attack is inside the
``Model::bossHitBox()``the consumer Thread get started.

This overridden ``mouseReleased()`` method sets the hero animation back to IDLE when released.

When the implemented method ``run()`` gets overridden, then the _consumer_ thread will execute the code
``HealthValue.INSTANCE.setBossHealth(10);`` which will lower the Boss health bar due to damage from the Hero.

### HealthValue.java
This Enum is a Singleton that implements the Runnable interface.
It also contains the BlockingQueue<Integer> _LifeSteal_ which handles the process of the _consumer and producer pattern._

The ``getBossHealth()`` method is a typical getter and provides the current health of the Boss that returns
the _Boss_Health_ int.

The ``setBossHealth`` method is a setter and updates the Boss health bar using the incoming integer,
adds the integer to the blockingQueue _LifeSteal_, and starts the _producer_ thread.

The overridden ``run()`` method contains the code
to take from the head of the blockingQueue _LifeSteal_ and add it to the Hero's health bar _Hero_health_
within a _try and catch._

### MovingValues.java
This Enum Singleton is keeping track of the hero's position on the screen using _xDealta_ and _yDealta_,
_animationIndex_, _animationFrame_ and _heroMove_.
This class is the _Subject_ of the Observer pattern,
and the constructor method adds observers to the ArrayList observers.


The ``getyDelta()`` method is typical getter and provides the hero's current position on the y-axel by
returning the _yDealta_.

The ``setyDelta()`` is a typical setter and updates the hero's position on the y-axel
reusing the _yDealta_.

The ``getxDelta()`` method is typical getter and provides the hero's current position on the x-axel by
returning the _xDealta_.

The ``setxDelta()`` is a typical setter and updates the hero's position on the x-axel
reusing the _xDealta_.

The ``getAnimationFrame()`` method is a getter for the _animationFrame_ variable.

The ``getHeroMove()`` method is a getter for the _heroMove_ variable.

The ``getAnimationIndex()`` method is a getter for the _animationIndex_ variable.

the ``setAnimationIndex`` is a setter and rewrite the _animationIndex_ variable with the parameter.

the ``setHeroMove`` is a setter and rewrite the _animationIndex_ variable with the parameter.

the ``setAnimationFrame`` is a setter and rewrite the _animationIndex_ variable with the parameter.

The two overridden methods ``register() and notifyObserver()`` are implemented and used but not the `unRegister()` method.

### Constants.java
This class provides the game with _global constants_ for the Hero's animation.

The only method inside this class is ``getHeroAnimation()`` which will return integer heroMove
for further use in ``Model::updateAnimation()`` to adjust the animation length.

## Discussion
### Purpose Fulfillment
The purpose of this project was to make a small section of a dungeon crawler game where the hero fights the last boss.
The game uses the MVC, EDT, Consumer & Producer, Observer Design Pattern,
and all of these has been successfully implemented into the game.

The MVC pattern was successfully implemented with: Create new objects of the two classes Model and View 
to be placed as parameter inside the Controller object. 
This ensures that they don't interact with each other as the requirements says,
"There may be no direct communication between models and views, where all actions needs to go through a controller.".

Previous attempts had a weak connection through static variables between the `Model` and the `View` to update the animation
of the hero animation. It's now replaced to the Enum Singleton class ``MovingValues`` and there by breaks that connection.


Previous attempts have been a `new Controller();` inside the Main method and inside the Controllers constructor method
was calling the Model and View classes.
This way is not only incorrect it also makes the MVC run on the top-Level thread from our ``Main()`` instead of EDT.

The Event Dispatch Thread (EDT) is activated in the ``Main()`` method and contains the MVC Pattern and the Swing window. 
Inside the Controller constructor the EDT is also executed with the game engine inside with graphical and event updates.
Everything to reassure that EDT is included and according to the requirements, 
"Ensure all graphical updates are executed on the Event Dispatch Thread (EDT)." and feedback from a previous attempt, 
"for these purposes, and note that this doesn't only refer to updates, but the creation of graphical components as well".

Previous attempt didn't have any EDT executed and made the whole game very unstable and filled with bugs. 

The Consumer and Producer pattern is implemented and used to make an effect of life steal while the hero is hitting 
the boss and draining his life while adding life to the hero's life bar and are implementation as described in Module 1.
The enum singleton `HealthValue` is created contains getter and setter on the boss's life bar and a BlockingQueue 
`LifeSteal` which gets filled with threads from the `MouseInputs` if the hero is in the boss hitbox, 
the method `bossHitBox()` determines if the hero is close enough.
While the hero is attacking inside the hitbox, a consumer thread is taking the value from the BlockingQueue `LifeSteal`
and adding it to the hero's life bar.

To make the code work as intended has been tricky since it includes a lot of classes and methods to cooperate properly
but was solved when threads and the BlockingQueue were implemented into the pattern.
There was a problem when the hero had killed the boss and attacks again, the health bar gained more life.
This was solved by an if-statement that if the boss is dead,
then the  BlockingQueue `LifeSteal` gets cleared from values and it that way there was no more values to add to
the hero's life bar.

To implement the Observer pattern the two interfaces `Subject` and `Observer` was created and the enum singleton 
`MovingValues` implements the `Subject` interface while both View and Controller implemented the `Observer` interface.
This way the Observer pattern updates the location of the hero on the screen when the user uses the keys 
"W, A, D and S".
The ``KeyInput`` class takes input from the user and sends the new `yDealta` and `xDelta` values through
`INSTANCE` setters.
The Subject implemented `MovingValues` sends updates to its _observers_ through the method `notifyObserver()` when 
either `yDealta` and `xDelta` is altered.
When the Observers get notified the `repaint()` function is executed and makes the hero moving on the screen.

This implementation was also tricky to make it actually work as intended, a lot of research was done to implement 
this pattern correctly and previous attempts had a backwards implemented version of the Observer Pattern.

### Alternative Approaches
The game works without the Consumer & Producer and Observer but was implemented due to the criteria for the project. 
And Template Design Pattern could be one for the enemies and one for the heroes since 
these two classes differ quite much and in future addition would have been better dynamical improvement with separate
Template design patterns.

The Consumer & Producer pattern is an overkill for the function _life steal_ in the game. 
It could easily be replaced with this line of code _HealthValue.INSTANCE.setBossHealth(10);_. 
As an example:

    @Override
    public void mouseClicked(MouseEvent e){
        if(e.getButton() == MouseEvent.BUTTON1) {
            Hero.setAnimation(ATTACK);
            if (HealthValue.INSTANCE.getBossHealth() >= 0) {
                if (Boss.bossHitBox()) {
                    HealthValue.INSTANCE.setBossHealth(10);
                }
            }
        } else{
            Hero.setAnimation(IDLE);
        }
    }



The Observer is a good option for a dungeon crawler game, but since this game is such a
small section, it could still run flawless with the _actionPerformed_ inside the ``Controller::actionPerformed()``. 
As an example:

     public class Controller{
     private Model model;
     private View view;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;

        SwingUtilities.invokeLater(() -> {

            Timer timer = new Timer(20, this);
            timer.start();

            view.addKeyListener(new KeyInput());
            view.addMouseListener(new MouseInputs());
            view.setFocusable(true);
            view.requestFocusInWindow();
        });
    }

        public void actionPerformed(ActionEvent e) {
            model.update();
            view.repaint();
        }
    }



## Personal Reflections
In this project, we have successfully created a dungeon crawler game section where the Hero fights the Boss with the
different Design Patterns included.

We also could provide alternative solutions that would work flawlessly as well but are not as suitable if the game itself
expends to multiple levels, sections, and with more characters.

In the making of the game, I truly felt happy to develop something that I love, but I crossed a lot of problems that 
I had to fix and it's the none reliable _addKeyListener(new KeyInput());_ that is responsible to the Hero's position.
No matter how much I did my research on it, and it never was 100% reliable, and using the Event Dispatch Thread fixed it.
The other KeyListener _addMouseListener(new MouseInputs());_
work flawlessly even without EDT.

However, this project has helped me understand the use of design patterns, streams, OOP, animations and programming in 
general extremely well.

The project has put my skill to the test, and I had to catch up to the criteria.
After this project, I would take this experience and try to make it as my new study strategy.