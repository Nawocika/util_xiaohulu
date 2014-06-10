package com.swing;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Scanlei1 extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    private Container contentPane;
    private JButton btn;
    private JButton[] btns;
    private JLabel b1;
    private JLabel b2;
    private JLabel b3;
    private Timer timer;//计时器
    private int row = 9;//初始化初级的格子为9*9
    private int col = 9;
    private int bon = 10;
    private int[][] a;//随机雷位置
    private int b;
    private int[] a1;
    private JPanel p, p1, p2, p3;

    /**
     * 初始化参数
     *
     * @param title
     */
    public Scanlei1(String title) {

        super(title);
        contentPane = getContentPane();
        setSize(297, 377);//设置窗口大小
        this.setBounds(400, 100, 400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//响应关闭

        //设置窗口

        timer = new Timer(1000, (ActionListener) this);//设置定时器
        a = new int[row + 2][col + 2];//初始化扫雷区域大小默认9*9
        initGUI();//初始化页面
    }

    /**
     * 初始化页面。默认显示为初级界面9*9
     */
    public void initGUI() {
        p3 = new JPanel();
        b = bon;

        JMenuBar menuBar = new JMenuBar();
        JMenu menu1 = new JMenu("游戏");
        JMenu menu2 = new JMenu("帮助");
        JMenuItem mi1 = new JMenuItem("初级");
        JMenuItem mi2 = new JMenuItem("中级");
        JMenuItem mi3 = new JMenuItem("高级");

        //设置菜单

        mi1.addActionListener(this);
        menu1.add(mi1);
        mi2.addActionListener(this);
        menu1.add(mi2);
        mi3.addActionListener(this);

        //每一个子菜单添加监听器

        menu1.add(mi3);
        menuBar.add(menu1);
        menuBar.add(menu2);
        p3.add(menuBar);

        //添加菜单

        b1 = new JLabel(bon + "");
        a1 = new int[bon];

        //设置雷数标签

        btn = new JButton("开始");
        btn.addActionListener(this);

        //设置开始按钮

        b2 = new JLabel("0");
        b3 = new JLabel("");
        btns = new JButton[row * col];

        //设置时间标签、信息标签、雷区按钮

        p = new JPanel();
        p.setLayout(new BorderLayout());
        contentPane.add(p);
        p.add(p3, BorderLayout.NORTH);

        //设置状态栏的面板

        p1 = new JPanel();

        p1.add(b1);
        p1.add(btn);
        p1.add(b2);
        p1.add(b3);

        p.add(p3, BorderLayout.NORTH);
        p.add(p1, BorderLayout.CENTER);

        //完善状态栏，添加所需信息
        p2 = new JPanel();
        p2.setLayout(new GridLayout(row, col, 0, 0));

        //初始化雷区的按钮放入面板
        for (int i = 0; i < row * col; i++) {
            btns[i] = new JButton("");
            btns[i].setMargin(new Insets(0, 0, 0, 0));
            btns[i].setFont(new Font(null, Font.BOLD, 25));
            btns[i].addActionListener(this);
            btns[i].addMouseListener(new NormoreMouseEvent());
            p2.add(btns[i]);
        }

        //设置雷区按钮
        contentPane.add(p, BorderLayout.NORTH);
        contentPane.add(p2, BorderLayout.CENTER);
        //添加状态栏和雷区
    }

    public void go() {
        setVisible(true);
        //设置窗体可见
    }

    public static void main(String[] args) {
        new Scanlei1("扫雷").go();

        //运行主函数-开始游戏
    }

    /**
     * 玩家点击的不是雷，则对此按钮附件的雷进行计算。
     * 计算并显示周围雷数量
     *
     * @param a
     * @param btns
     * @param e
     * @param i
     * @param x
     * @param y
     */
    public void out(int[][] a, JButton[] btns, ActionEvent e, int i, int x, int y) {
        int p = 1;

        if (a[x][y] == 0) {
            a[x][y] = 10;
            btns[i].setEnabled(false);    //33
            for (int l = y - 1; l <= y + 1; l++) {
                int m = x - 1 - 1;
                int n = l - 1;
                p = 1;
                System.out.println(a[1][2]);
                if (n > -1 && n < col && m > -1 && m < row) {
                    for (int q = 0; q < row && p == 1; q++) {//col-->row;
                        if (((n + col * q) >= (m * col)) && ((n + col * q) < (m + 1) * col)) {
                            if (a[x - 1][l] != 0 && a[x - 1][l] != 10) {
                                btns[n + col * q].setText(a[x - 1][l] + "");
                                a[x - 1][l] = 10;
                                btns[n + col * q].setEnabled(false);
                            } else if (a[x - 1][l] == 0) {
                                //a[x-1][l]=10;
                                btns[n + col * q].setEnabled(false);
                                out(a, btns, e, n + col * q, x - 1, l); ////55////
                                a[x - 1][l] = 10;
                                btns[n + col * q].setEnabled(false);
                            }
                            p = 0;

                        }
                    }
                }
                p = 1;
                m = x;
                if (n > -1 && n < col && m > -1 && m < col) {
                    for (int q = 0; q < row && p == 1; q++) {
                        if (((n + col * q) >= (m * col)) && ((n + col * q) < (m + 1) * col)) {
                            if (a[x + 1][l] != 0 && a[x + 1][l] != 10) {
                                btns[n + col * q].setText(a[x + 1][l] + "");
                                a[x + 1][l] = 10;
                                btns[n + col * q].setEnabled(false);
                            } else if (a[x + 1][l] == 0) {

                                out(a, btns, e, n + col * q, x + 1, l);///55////
                                a[x + 1][l] = 10;
                                btns[n + col * q].setEnabled(false);
                            }
                            p = 0;
                        }
                    }

                }
            }
            int m = x - 1;
            int n = y - 1 - 1;
            p = 1;
            if (n > -1 && n < col && m > -1 && m < col) {
                for (int q = 0; q < row && p == 1; q++) {
                    if (((n + col * q) >= (m * col)) && ((n + col * q) < (m + 1) * col)) {
                        if (a[x][y - 1] != 0 && a[x][y - 1] != 10) {
                            btns[n + col * q].setText(a[x][y - 1] + "");
                            a[x][y - 1] = 10;
                            btns[n + col * q].setEnabled(false);
                        } else if (a[x][y - 1] == 0) {


                            out(a, btns, e, n + col * q, x, y - 1);

                            a[x][y - 1] = 10;
                            btns[n + col * q].setEnabled(false);
                        }
                        p = 0;
                    }
                }
            }
            p = 1;
            m = x - 1;
            n = y + 1 - 1;
            if (n > -1 && n < col && m > -1 && m < col) {
                for (int q = 0; q < row && p == 1; q++) {
                    if (((n + col * q) >= (m * col)) && ((n + col * q) < (m + 1) * col)) {
                        if (a[x][y + 1] != 0 && a[x][y + 1] != 10) {
                            btns[n + col * q].setText(a[x][y + 1] + "");
                            a[x][y + 1] = 10;
                            btns[n + col * q].setEnabled(false);
                        } else if (a[x][y + 1] == 0) {
                            out(a, btns, e, n + col * q, x, y + 1);
                            a[x][y + 1] = 10;
                            btns[n + col * q].setEnabled(false);
                        }
                        p = 0;
                    }
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand() == "初级") {
            row = 9;
            col = 9;
            bon = 10;
            a1 = new int[bon];
            b = bon;
            //setSize(297,377);
            a = new int[row + 2][col + 2];
            this.remove(p2);
            timer.stop();

            //初始化雷数及格数

            b1.setText("10");
            b2.setText("0");
            b3.setText("");
            btns = new JButton[row * col];
            p2 = new JPanel();
            p2.setLayout(new GridLayout(row, col, 0, 0));

            //初始化信息面板及网格

            for (int i = 0; i < row * col; i++) {
                btns[i] = new JButton("     ");
                btns[i].setMargin(new Insets(0, 0, 0, 0));
                btns[i].setFont(new Font(null, Font.BOLD, 25));
                btns[i].addActionListener(this);
                btns[i].addMouseListener(new NormoreMouseEvent());
                p2.add(btns[i]);
            }
            contentPane.add(p2, BorderLayout.CENTER);
            //setSize(297,377);
            this.pack();

            //设置网格

            for (int i = 0; i < row * col; i++) {
                btns[i].setText(" ");
                btns[i].setEnabled(true);
            }
            for (int i = 0; i < row + 2; i++) {
                for (int j = 0; j < col + 2; j++) {
                    a[i][j] = 0;
                }
            }

            //将所有格设置为0

        } else if (e.getActionCommand() == "中级") {
            row = 16;
            col = 16;
            bon = 40;
            //setSize(33*col,33*row+80);
            a1 = new int[bon];
            a = new int[row + 2][col + 2];
            b = bon;
            this.remove(p2);
            timer.stop();

            //初始化雷数及格数

            b1.setText("40");
            b2.setText("0");
            b3.setText("");
            btns = new JButton[row * col];
            p2 = new JPanel();
            p2.setLayout(new GridLayout(row, col, 0, 0));

            //初始化信息面板及网格

            for (int i = 0; i < row * col; i++) {
                btns[i] = new JButton("    ");
                btns[i].setMargin(new Insets(0, 0, 0, 0));
                btns[i].setFont(new Font(null, Font.BOLD, 25));
                btns[i].addActionListener(this);
                btns[i].addMouseListener(new NormoreMouseEvent());
                p2.add(btns[i]);
            }
            contentPane.add(p2, BorderLayout.CENTER);
            this.pack();
            //setSize(33*col,33*row+80);

            //设置网格

            for (int i = 0; i < row * col; i++) {
                btns[i].setText("");
                btns[i].setEnabled(true);
            }
            for (int i = 0; i < row + 2; i++) {
                for (int j = 0; j < col + 2; j++) {
                    a[i][j] = 0;
                }
            }

            //将所有格设置为0

        } else if (e.getActionCommand() == "高级") {
            row = 16;
            col = 32;
            bon = 99;
            setSize(33 * col, 33 * row + 80);
            a1 = new int[bon];
            a = new int[row + 2][col + 2];
            b = bon;
            this.remove(p2);
            timer.stop();

            //初始化雷数及格数

            b1.setText("99");
            b2.setText("0");
            b3.setText("");
            btns = new JButton[row * col];
            p2 = new JPanel();
            p2.setLayout(new GridLayout(row, col, 0, 0));

            //初始化信息面板及网格

            for (int i = 0; i < row * col; i++) {
                btns[i] = new JButton("    ");
                btns[i].setMargin(new Insets(0, 0, 0, 0));
                btns[i].setFont(new Font(null, Font.BOLD, 25));
                btns[i].addActionListener(this);
                btns[i].addMouseListener(new NormoreMouseEvent());
                p2.add(btns[i]);
            }
            contentPane.add(p2, BorderLayout.CENTER);
            //setSize(33*col,33*row+80);
            this.pack();

            //设置网格

            for (int i = 0; i < row * col; i++) {
                btns[i].setText("");
                btns[i].setEnabled(true);
            }
            for (int i = 0; i < row + 2; i++) {
                for (int j = 0; j < col + 2; j++) {
                    a[i][j] = 0;
                }
            }

            //将所有格设置为0

        }

        //如果点击的时开始按钮
        if (e.getSource() == btn) {
            timer.start();
            b = bon;
            b3.setText("");
            //System.out.println(bon);

            for (int i = 0; i < row * col; i++) {
                btns[i].setText("");
                btns[i].setEnabled(true);
            }
            for (int i = 0; i < row + 2; i++) {
                for (int j = 0; j < col + 2; j++) {
                    a[i][j] = 0;
                }
            }

            //清空

            for (int i = 0; i < bon; i++) {
                int p = 1;
                int m = (int) (Math.random() * row * col);
                while (p == 1) {
                    int l = 1;
                    int j;
                    for (j = 0; j < i && l == 1; j++) {

                        if (a1[j] == m) {
                            m = (int) (Math.random() * row * col);
                            l = 0;
                        }
                    }
                    if (j == i) {
                        a1[i] = m;
                        p = 0;
                    }
                }
            }
            b1.setText(bon + "");
            b2.setText("0");

            //产生随机数
            //布雷
            for (int i = 0; i < bon; i++) {
                int x = (a1[i] / col + 1);
                int y = (a1[i] % col + 1);
                a[x][y] = 100;
            }
            for (int i = 0; i < row + 2; i++) {
                for (int j = 0; j < col + 2; j++) {
                    if (i == 0 || j == 0 || i == row + 1 || j == col + 1) {
                        a[i][j] = 0;
                    }
                }
            }
            for (int i = 1; i <= row; i++) {
                for (int j = 1; j <= col; j++) {
                    if (a[i][j] != 100) {
                        for (int l = j - 1; l <= j + 1; l++) {
                            if (a[i - 1][l] == 100) {
                                a[i][j]++;
                            }
                            if (a[i + 1][l] == 100) {
                                a[i][j]++;
                            }
                        }
                        if (a[i][j - 1] == 100) {
                            a[i][j]++;
                        }
                        if (a[i][j + 1] == 100) {
                            a[i][j]++;
                        }
                    }
                }
            }
        }
        if (e.getSource() == timer) {
            String time = b2.getText().trim();

            int t = Integer.parseInt(time);
            //System.out.println(t);
            if (t >= 600) {
                timer.stop();
            } else {

                t++;
                b2.setText(t + "");
            }
        }

        /**
         * 如果点击的是雷区的按钮，则判断点击是什么按钮，如果是雷，直接报你输了，如果不是类，调用out方法进行范围内雷的数量
         */
        for (int i = 0; i < col * row; i++) {
            if (btns[i].getText() != "★") {
                int x = i / col + 1;
                int y = i % col + 1;
                if (e.getSource() == btns[i] && a[x][y] == 100) {
                    btns[i].setText("★");
                    btns[i].setEnabled(false);
                    a[x][y] = 10;
                    for (int k = 0; k < col * row; k++) {
                        int m1 = k / col + 1;
                        int n1 = k % col + 1;
                        if (a[m1][n1] != 10 && btns[k].getText() == "★") {
                            btns[k].setText("*o*");
                        }
                    }
                    for (int j = 0; j < col * row; j++) {
                        int m = j / col + 1;
                        int n = j % col + 1;
                        if (a[m][n] == 100) {
                            btns[j].setText("★");
                            btns[j].setEnabled(false);
                            b3.setText("你输了 ！！");
                        }
                        btns[j].setEnabled(false);
                        a[m][n] = 10;
                    }
                    timer.stop();
                } else if (e.getSource() == btns[i]) {
                    if (a[x][y] == 0) {
                        out(a, btns, e, i, x, y);
                        a[x][y] = 10;
                        btns[i].setEnabled(false);
                    }
                    if (a[x][y] != 0 && a[x][y] != 10) {
                        btns[i].setText(a[x][y] + "");
                        btns[i].setEnabled(false);
                        a[x][y] = 10;
                    }
                }
            } else if (btns[i].getText() == "★") {
            }
        }
    }

    //鼠标右击事件标记为雷
    class NormoreMouseEvent extends MouseAdapter {
        public void mouseClicked(MouseEvent e) {
            System.out.println(b);
            for (int i = 0; i < col * row; i++) {
                int x1 = i / col + 1;
                int y1 = i % col + 1;
                if (e.getSource() == btns[i] && btns[i].getText() != "★" && a[x1][y1] != 10) {
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        btns[i].setText("★");
                        b--;
                        if (b == 0) {
                            int flag = 0;
                            for (int j = 0; j < col * row; j++) {
                                int x = j / col + 1;
                                int y = j % col + 1;
                                if (a[x][y] == 100 && btns[j].getText() == "★") {
                                    flag++;
                                }
                            }
                            if (flag == bon) {
                                timer.stop();
                                b3.setText("你赢了！");
                            }
                        }
                        b1.setText(b + "");
                    }
                } else if (e.getSource() == btns[i] && btns[i].getText() == "★" && a[x1][y1] != -1) {
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        btns[i].setText("");
                        b++;
                        if (b > bon) {
                            b1.setText(bon + "");
                        } else {
                            b1.setText(b + "");
                        }
                        btns[i].setEnabled(true);
                    }
                }
            }
        }
    }
}

