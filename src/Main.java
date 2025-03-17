import javax.swing.*;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;
import java.util.Random;

public class Main {
    private static int lastKeyPressed; // посленяя нажатая кнока
    private static boolean los;
    //метод постройки ---------
    public static StringBuffer  Game (int SizeFiedGame, char frameOther){

        StringBuffer base = new StringBuffer("");
        StringBuffer field = new StringBuffer("");
        StringBuffer BuildField = new StringBuffer("");

        for (int i = 0; i < SizeFiedGame-1; i++) {                 // строим низ верх
            base.append(frameOther);
            base.append(frameOther);
        }
        base.append(frameOther);

        field.append('\n');
        field.append(frameOther);                                   // строим середину
        for (int i = 0; i < SizeFiedGame*2-3; i++) {
            field.append(' ');

        }
        field.append(frameOther);
        BuildField.append(base);                                    // складываем все

        for (int i = 0; i < SizeFiedGame - 2; i++) {
            BuildField.append(field);
        }
        BuildField.append('\n');
        BuildField.append(base);

        return BuildField;
    }

    public static void main(String[] args) throws InterruptedException {
        for ( ; ; ) {
            // Окно
            los = false;
            JFrame frame = new JFrame("змейка");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JTextArea textArea = new JTextArea();

            JScrollPane scrollPane = new JScrollPane(textArea);
            frame.add(scrollPane, BorderLayout.CENTER);

            frame.setSize(700, 700);
            frame.setVisible(true);
            textArea.setEditable(false);

            Font monoFont = new Font("Monospaced", Font.PLAIN, 20);
            textArea.setFont(monoFont);

            // размер и прочее----------

            int DirectionMove = 119;
            int SizeFiedGame = 20;
            int Duration = 14;

            char frame1 = '0';
            char TailSnake = '#';
            char HeadShanke = '@';
            char BodySnake = 'x';
            char Point = '+';

            if (SizeFiedGame % 2 == 0) {
                SizeFiedGame--;
            }

            StringBuffer FieldGame = new StringBuffer();
            FieldGame = Game(SizeFiedGame, frame1);

            int Head = (SizeFiedGame / 2) * (SizeFiedGame * 2) - SizeFiedGame;
            int Tail = (SizeFiedGame / 2) * (SizeFiedGame * 2) + SizeFiedGame;          //корды хвоста

            FieldGame.setCharAt(Head, HeadShanke);
            FieldGame.setCharAt(Tail, TailSnake);

            Vector<Integer> lehgtnSnake = new Vector<>();
            int ConstantTransition = SizeFiedGame * 2;
            boolean Lose1 = false;

            lehgtnSnake.add(Tail);
            lehgtnSnake.add(Head);

            textArea.setText("" + FieldGame);
            textArea.append("\nГотов? управление WASD, для начала нажми любую клавишу\n(желательно англ. раскладка)");

            for (; lastKeyPressed == 0; ) {
                textArea.addKeyListener(new KeyListener() {
                    public void keyTyped(KeyEvent e) {
                    }
                    public void keyReleased(KeyEvent e) {
                    }
                    public void keyPressed(KeyEvent e) {
                        String Key = String.valueOf(e.getKeyChar());
                        lastKeyPressed = (int) Key.charAt(0);
                    }
                });
                Thread.sleep(500);
            }

            Random random = new Random();
            for (int DurationRef = 0; Lose1 == false; DurationRef++) {                // игра
                Thread.sleep(Duration);                                               // спим?
                if (DurationRef == 80) {
                    DurationRef -= 80;
                    for (; ; ) {
                        int randomPoint = random.nextInt(32767) % (SizeFiedGame * SizeFiedGame * 2);
                        if (FieldGame.charAt(randomPoint) == ' ' && randomPoint % 2 != 0) {
                            FieldGame.setCharAt(randomPoint, Point);
                        }
                        break;
                    }
                }
                textArea.addKeyListener(new KeyListener() {
                    public void keyTyped(KeyEvent e) {
                    }

                    public void keyReleased(KeyEvent e) {
                    }

                    public void keyPressed(KeyEvent e) {
                        String Key = String.valueOf(e.getKeyChar());
                        lastKeyPressed = (int) Key.charAt(0);

                    }
                });
                if (lastKeyPressed != 0) {   // чекаем кнопки 97 и 228 ... 119 и 230 ...  115 и 235 .. 100 и 162
                    int DirectionMoveCheck = lastKeyPressed;
                    if ((DirectionMoveCheck == 97 || DirectionMoveCheck == 228) && (DirectionMove == 100 || DirectionMove == 162)) {
                        // невозможно повернуть
                    } else if ((DirectionMoveCheck == 100 || DirectionMoveCheck == 162) && (DirectionMove == 97 || DirectionMove == 228)) {
                        // невозможно повернуть
                    } else if ((DirectionMoveCheck == 119 || DirectionMoveCheck == 230) && (DirectionMove == 115 || DirectionMove == 235)) {
                        // невозможно повернуть
                    } else if ((DirectionMoveCheck == 115 || DirectionMoveCheck == 235) && (DirectionMove == 119 || DirectionMove == 230)) {
                        // невозможно повернуть
                    } else {
                        DirectionMove = DirectionMoveCheck;
                    }
                }

                if (DurationRef % 10 == 1) {
                    switch (DirectionMove) {
                        case 97:
                        case 228:                               // влево
                            if (FieldGame.charAt(Head - 1) == frame1 || FieldGame.charAt(Head - 1) == TailSnake || FieldGame.charAt(Head - 1) == BodySnake) {
                                Lose1 = true;
                            }
                            if (FieldGame.charAt(Head - 2) == Point) {
                                Head -= 2;
                                lehgtnSnake.add(Head);
                            } else if (FieldGame.charAt(Head - 2) == ' ') {
                                FieldGame.setCharAt(lehgtnSnake.firstElement(), ' ');
                                lehgtnSnake.remove(lehgtnSnake.firstElement());
                                Head -= 2;
                                lehgtnSnake.add(Head);
                            } else {
                                textArea.append("\nerorr 1 case97");
                            }

                            break;

                        case 119:               // вверх
                        case 230:
                            if (FieldGame.charAt(Head - ConstantTransition) == frame1 || FieldGame.charAt(Head - ConstantTransition) == TailSnake || FieldGame.charAt(Head - ConstantTransition) == BodySnake) {
                                Lose1 = true;
                            } else if (FieldGame.charAt(Head - ConstantTransition) == Point) {
                                Head -= ConstantTransition;
                                lehgtnSnake.add(Head);
                            } else if (FieldGame.charAt(Head - ConstantTransition) == ' ') {
                                FieldGame.setCharAt(lehgtnSnake.firstElement(), ' ');
                                lehgtnSnake.remove(lehgtnSnake.firstElement());
                                Head -= ConstantTransition;
                                lehgtnSnake.add(Head);
                            } else {
                                textArea.append("\nerorr 2 case 119");
                            }
                            break;

                        case 115:                       // вниз
                        case 235:
                            if (FieldGame.charAt(Head + ConstantTransition) == frame1 || FieldGame.charAt(Head + ConstantTransition) == TailSnake || FieldGame.charAt(Head + ConstantTransition) == BodySnake) {
                                Lose1 = true;
                            }
                            if (FieldGame.charAt(Head + ConstantTransition) == Point) {
                                Head += ConstantTransition;
                                lehgtnSnake.add(Head);
                            } else if (FieldGame.charAt(Head + ConstantTransition) == ' ') {
                                FieldGame.setCharAt(lehgtnSnake.firstElement(), ' ');
                                lehgtnSnake.remove(lehgtnSnake.firstElement());
                                Head += ConstantTransition;
                                lehgtnSnake.add(Head);
                            } else {
                                textArea.append("\nerorr 3 case115");
                            }
                            break;

                        case 100:                   // вправо
                        case 162:
                            if (FieldGame.charAt(Head + 1) == frame1 || FieldGame.charAt(Head + 1) == TailSnake || FieldGame.charAt(Head + 1) == BodySnake) {
                                Lose1 = true;
                            } else if (FieldGame.charAt(Head + 2) == Point) {
                                Head += 2;
                                lehgtnSnake.add(Head);
                            } else if (FieldGame.charAt(Head + 2) == ' ') {
                                FieldGame.setCharAt(lehgtnSnake.firstElement(), ' ');
                                lehgtnSnake.remove(lehgtnSnake.firstElement());
                                Head += 2;
                                lehgtnSnake.add(Head);
                            } else {
                                textArea.append("\neror 4 case100");
                            }
                            break;

                        default:
                            textArea.append("\nerorr 00000 case 0000");
                            break;


                    }

                    for (int i = 0; i < lehgtnSnake.size(); i++) {
                        if (i == 0) {
                            FieldGame.setCharAt(lehgtnSnake.firstElement(), TailSnake);
                        } else if (i == lehgtnSnake.size() - 1) {
                            FieldGame.setCharAt(lehgtnSnake.get(i), HeadShanke);
                        } else {
                            FieldGame.setCharAt(lehgtnSnake.get(i), BodySnake);
                        }
                    }
                    textArea.setText("");
                    textArea.setText("" + FieldGame);
                    textArea.append("\nнабрано очков: \t\t" + (lehgtnSnake.size() - 2));

                }
            }

            textArea.append("\n ВЫ ПРОИГРАЛИ");

            JButton button = new JButton("Начать заного?");
            for (; los != true; ) {
                button.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        los = true;

                    }
                });
                frame.add(button, BorderLayout.SOUTH);
                frame.setVisible(true);
            }
            lastKeyPressed = 0;
            frame.dispose();
        }
    }
}
