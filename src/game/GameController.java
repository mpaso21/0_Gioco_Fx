package game;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utility.WrapperValue;
//gAME CONTROLLER istanzia tutti gli oggetti e crea il loop del gioco
                                            // COMMENTO 1
                                            //root di tipo parent che era il genitore della scena del menu
                                            //gamecontroller di tipo group è il nuovo genitore della scena che sto creando
                                            // COMMENTO 2
public class GameController extends Group { //extend Group che � il nodo padre. Nel controller precedente cio� quello associato
                                             //al caricamento del menu era GridPane (Parent pi� generale)

    private AnimationTimer at;
    private WorldModel model;
    private WorldView view;
    private World world;

    public GameController(Stage primaryStage) {
        world = new World(primaryStage);//contiene le cose
        model = new WorldModel(world);//muovo le cose
        //this si riferisce a group
        view = new WorldView(this, world);//visualizzo le cose this game controller/gruppo per buttarci dentro la canvas
        start();//faccio partire loop gioco
        world.setAnimationTimer(at);// la passa dfopo lo start cosi non è null. creo questo per far si che nel world posso chiamare animation.stop
    }

    private void start() {//loop gioco thread principale in cui continuo a chiamare handle

        final long startNanoTime = System.nanoTime(); //startNanoTime, tempo iniziale

        at = new AnimationTimer() {//interfaccia funzionale, devo implementare il metodo handle
            //60 volte al secondo chiamo handle

            WrapperValue<Long> lastNanoTime = new WrapperValue<Long>(System.nanoTime());//aatributo classe animationTimer //creo un wrapper un solo elemento long

            @Override
            public void handle(long currentNanoTime) {
                   //tempo trascorso dall'ultimachiamata del metodo handle = tempo attuale - tempo ultima verifica 0,017 circa 1/60
                double elapsedTime = (currentNanoTime - lastNanoTime.value) / 1000000000.0; //costante è pari a 0.016  ma lo calcolo per evitare che se ci fossero dei lag l'update avvenga in maniera corretta
                lastNanoTime.value = currentNanoTime;
                //t tempo trascorso dall'inizio
                //t = tempo attuale - tempo di inzioi(primA volta che ho chiamaTO start
                double t = (currentNanoTime - startNanoTime) / 1000000000.0;

             
                model.update(elapsedTime, t);
                view.update(elapsedTime);//render view
            }

        };
        //quando si ferma? quando il player si schianta esce game over e la scena dopo 3 sec viene risettata con il menu(chiamo at stop)
        at.start();//start animation timeer

    }
    //inizializza gli eventi della tastiera sulla scena
    public void initEvents(Scene scena) {
        //quando clicco tasto tastiera
        scena.setOnKeyPressed(e -> { //premo tasto
            String code = e.getCode().toString();//mi viene passato l'evento dal motore di java
            if (!model.input.contains(code)) {

                if (code.equals("X")) {//fa in modo che la x non rimanga premuta, mentre tutti gli altri tasti possono rimanere premuti+
                    //sparo un bullets alla volta non a raffica mentre il jump clicco e continuo ad andare in alto fino a quando tocco il bordo
                    if (!model.shoot.value) {
                        model.input.add(code);
                    }
                } else {
                    model.input.add(code);
                }
            }
        });

        scena.setOnKeyReleased(e -> { //rilascio tasto
            String code = e.getCode().toString();
            if (code.equals("X")) {
                model.shoot.value = false;
                model.input.remove(code);

            } else {
                model.input.remove(code);

            }

        });

    }
}
