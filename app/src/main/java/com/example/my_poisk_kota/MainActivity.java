package com.example.my_poisk_kota;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

        MediaPlayer mediaPlayer;
        private TextView coordinatesOut;// окно поиска кортинки
        private TextView coordinatesDown;// окно вывода значений координат нажатия
        private TextView coordinatesMove;// окно вывода значений координат движдения
        private TextView coordinatesUP;// окно вывода значений отпускания
        private float x; // задание поля для координаты X
        private float y; // задание поля для координаты Y

        private String sDown; // строка для записи координат нажатия
        private String sMove; // строка для записи координат движения
        private String sUp; // строка для записи координат отпускания

        // задание дополнительных полей координат кота Шрёдингера
        private final float xCat = 500; // задание поля для координаты X
        private final float yCat = 500; // задание поля для координаты Y
        private final float deltaCat = 50; // допустимая погрешность в нахождении кота

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            mediaPlayer=MediaPlayer.create(this,R.raw.z);

            // присваивание переменной активити элемента представления activity_main
            coordinatesOut = findViewById(R.id.coordinatesOut);
            coordinatesDown=findViewById(R.id.coordinatesDown);
            coordinatesMove=findViewById(R.id.coordinatesMove);
            coordinatesUP=findViewById(R.id.coordinatesUP);

            // выполнение действий при касании экрана
            coordinatesOut.setOnTouchListener(listener);
        }

        // объект обработки касания экрана (слушатель)
        private View.OnTouchListener listener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) { // в motionEvent пишутся координаты
                x = motionEvent.getX(); // инициализация координат X
                y = motionEvent.getY(); // инициализация координат Y
                String resultX = String.format("%.2f",x);// округление координаты х до сотых
                String resultY = String.format("%.2f",y);// округление координаты у до сотых
                switch (motionEvent.getAction()) { // метод getAction() считывает состояние касания (нажатие/движение/отпускание)
                    case MotionEvent.ACTION_DOWN: // нажатие
                        sDown = "X=" +resultX + ",  Y=" +resultY;
                        coordinatesDown.setText(sDown);// Вывод на экран
                        break;

                    case MotionEvent.ACTION_MOVE: // движение
                        sMove = "X=" + resultX + ", Y=" + resultY;
                        // задание условия нахождения кота Шрёдингера
                        if (x < (xCat + deltaCat) && x > (xCat - deltaCat) && y < (yCat + deltaCat) && y > (yCat - deltaCat)) { // если пользователь коснулся места нахождения кота
                            // размещаем тост (контекст, сообщение, длительность сообщения)
                            Toast toast = Toast.makeText(getApplicationContext(),"ВЫ НАШЛИ КОТА", Toast.LENGTH_SHORT); // инициализация
                            toast.setGravity(Gravity.CENTER, 0,0); // задание позиции на экране (положение, смещение по оси Х, смещение по оси Y)
                            // помещение тоста в контейнер
                            LinearLayout toastContainer = (LinearLayout) toast.getView();
                            // добавление в тост картинки
                            ImageView cat = new ImageView(getApplicationContext()); // создание объекта картинки (контекст)
                            cat.setImageResource(R.drawable.found_cat); // добавление картинки из ресурсов
                            toastContainer.addView(cat, 1); // добавление картинки под индексом 1 в имеющийся контейнер
                            toast.show(); // демонстрация тоста на экране
                            mediaPlayer.start();
                        }coordinatesMove.setText(sMove);
                        break;
                    case MotionEvent.ACTION_UP: // отпускание
                    case MotionEvent.ACTION_CANCEL: // внутрений сбой (аналогичен ACTION_UP)
                        sUp = "X=" + resultX + ", Y=" + resultY;
                        coordinatesUP.setText(sUp);
                        break;
                }

                return true; // подтверждение нашей обработки событий
            }
        };
    }

