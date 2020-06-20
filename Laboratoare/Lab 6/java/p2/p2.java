// skel PA 2017

import java.util.Scanner;

/**
 * Clasa cu 2 membri de orice tip
 * Echivalent cu std::pair din C++
 */
class Pair<F, S> {
    public F first;
    public S second;

    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }
}

/**
 * Reprezinta o mutare efectuata de un jucator
 */
class Move {
    public int player, x, y;

    public Move(int player, int x, int y) {
        this.player = player; /* Jucatorul care face mutarea */
        this.x = x; /* Linia */
        this.y = y; /* Coloana */
    }

    public Move(int player) {
        this(player, -1, -1);
    }
}

/**
 * Reprezinta starea jocului
 */
class Reversi {
    public static int Inf = 123456789;
    public static int N = 6; /* Pastrati N par, N >= 4 */

    private int data[][];

    public Reversi() {
        data = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                data[i][j] = 0;
        data[N / 2 - 1][N / 2 - 1] = data[N / 2][N / 2] = 1;
        data[N / 2 - 1][N / 2] = data[N / 2][N / 2 - 1] = -1;
    }

    /**
     * Intoarce true daca jocul este intr-o stare finala
     */
    public boolean ended() {
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++) {
                Reversi tmp = (Reversi) this.clone();
                if (tmp.apply_move(new Move(1, i, j)))
                    return false;
                tmp = (Reversi) this.clone();
                if (tmp.apply_move(new Move(-1, i, j)))
                    return false;
            }
        return true;
    }

    /**
     * Returns 1 if player won, 0 if draw and -1 if lost
     */
    int winner(int player) {
        if (!ended())
            return 0;
        int s = 0;
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
            {
                if (data[i][j] == player)
                    s++;
                else if (data[i][j] == -player)
                    s--;
            }
        return s > 0 ? 1 : s == 0 ? 0 : -1;
    }

    /**
     * Functia de evaluare a starii curente a jocului
     * Evaluarea se face din perspectiva jucatorului
     * aflat curent la mutare (player)
     */
    public int eval(int player) {
        /**
         * TODO Implementati o functie de evaluare
         * Aceasta trebuie sa intoarca:
         * Inf daca jocul este terminat in favoarea lui player
         * -Inf daca jocul este terminat in defavoarea lui player
         *
         * In celelalte cazuri ar trebui sa intoarca un scor cu atat
         * mai mare, cu cat player ar avea o sansa mai mare de castig
         */
        if(winner(player) == 1)
            return Inf;
        else if(winner(-player) == 1)
            return -Inf;

        int eval = 0;

        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                if(data[i][j] == player)
                    eval++;
                else
                    eval--;
        return eval;
    }

    /**
     * Aplica o mutare a jucatorului, modificand starea jocului
     * Plaseaza piesa jucatorului move.player in pozitia move.x, move.y
     * Mutarea move.x == -1, move.y == -1 semnifica ca jucatorul
     * paseaza randul la mutare
     * Returneaza true daca mutarea este valida
     */
    public boolean apply_move(Move move) {
        if (move.x == -1 && move.y == -1)
            return true;

        if (data[move.x][move.y] != 0)
            return false;

        boolean ok = false;

        for (int x_dir = -1; x_dir <= 1; x_dir++)
            for (int y_dir = -1; y_dir <= 1; y_dir++) {
                if (x_dir == 0 && y_dir == 0)
                    continue;
                int i = move.x + x_dir, j = move.y + y_dir;
                for (; i >= 0 && j >= 0 && i < N && j < N && data[i][j] == -move.player; i += x_dir, j += y_dir);
                if (i >= 0 && j >= 0 && i < N && j < N && data[i][j] == move.player && (P2.abs(move.x - i) > 1 || P2.abs(move.y - j) > 1)) {
                    ok = true;
                    for (i = move.x + x_dir, j = move.y + y_dir; i >= 0 && j >= 0 && i < N && j < N && data[i][j] == -move.player; i += x_dir, j += y_dir)
                        data[i][j] = move.player;
                }
            }

        if (!ok)
            return false;

        data[move.x][move.y] = move.player;

        return true;
    }

    /**
     * Afiseaza starea jocului
     */
    public String toString() {
        String ret = "";
        for (int i = 0; i < N; i++) {
            for (int j = 0; j< N; j++) {
                if (data[i][j] == 0)
                    ret += ".";
                else if (data[i][j] == 1)
                    ret += "O";
                else
                    ret += "X";
                ret += " ";
            }
            ret += "\n";
        }
        ret += "\n";

        return ret;
    }

    /**
     * Intoarce o copie a starii de joc
     */
    public Object clone() {
        Reversi ret = new Reversi();
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                ret.data[i][j] = data[i][j];
        return ret;
    }
}

class P2 {
    static int abs(int x) {
        return x > 0 ? x : -x;
    }

    static Pair<Integer, Move> minimax(Reversi init, int player, int depth) {
        /**
         * TODO Implementati conditia de oprire
         */
        if(init.ended() || depth == 0) {
            Pair<Integer, Move> nothing = new Pair<>(init.eval(player), new Move(player));
            return nothing;
        }
        int max = -Reversi.Inf;
        Move best = new Move(player, -1, -1); //Miscarea default
        for(int i = 0; i < init.N; i++) { //Nu are get moves
            for (int j = 0; j < init.N; j++) { //Incerc peste tot atunci
                Reversi possible = (Reversi) init.clone();
                Move current_move = new Move(player,i, j);
                if(possible.apply_move(current_move)) {
                    Pair<Integer, Move> result= minimax(possible, player,depth -1);
                    Integer aux = - result.first;
                    if(aux > max) {
                        max = aux;
                        best = current_move;
                    }
                }
            }
        }



        /**
         * TODO Determinati mutarile posibile
         * si determinati cel mai bun scor si cea mai buna mutare
         * folosind algoritmul minimax
         *
         * Fiti atenti ca daca player nu poate efectua nici o mutare, el
         * poate ceda randul la mutat prin mutarea conventionala <player, -1, -1>
         */

        return new Pair<Integer, Move>(max, best);
    }

    /**
     * Implementarea de negamax cu alpha-beta pruning
     * Intoarce o pereche <x, y> unde x este cel mai bun scor
     * care poate fi obtinut de jucatorul aflat la mutare,
     * iar y este mutarea propriu-zisa
     */
    static Pair<Integer, Move> minimax_abeta(Reversi init, int player, int depth, int alfa, int beta) {
        /* Conditia de oprire */

        /**
         * TODO Determinati mutarile posibile
         * si determinati cel mai bun scor si cea mai buna mutare
         * folosind algoritmul minimax cu alfa-beta pruning
         *
         * Fiti atenti ca daca player nu poate efectua nici o mutare, el
         * poate ceda randul la mutat prin mutarea conventionala <player, -1, -1>
         */
        if(init.ended() || depth == 0) {
            Pair<Integer, Move> nothing = new Pair<>(init.eval(player), new Move(player));
            return nothing;
        }
        //int max = -Reversi.Inf;
        Move best = new Move(player, -1, -1); //Miscarea default
        for(int i = 0; i < init.N; i++) { //Nu are get moves
            for (int j = 0; j < init.N; j++) { //Incerc peste tot atunci
                Reversi possible = (Reversi) init.clone();
                Move current_move = new Move(player,i, j);
                if(possible.apply_move(current_move)) {
                    Pair<Integer, Move> result= minimax_abeta(possible, player,depth -1, -beta, -alfa);
                    Integer aux = -result.first;
                    if(best.x == -1)
                        best = current_move; //Orice miscare e mai buna decat sa nu faca absolut nimic
                    if(aux > alfa) {
                        alfa = aux;
                        best = current_move;
                    }
                    if (alfa >= beta && best.x != -1) {
                        break;
                    }
                }
            }
        }

        return new Pair<Integer, Move>(alfa, best);
    }

    public static void main(String [] args) {
        Reversi rev = new Reversi();
        System.out.print(rev);

        /* Choose here if you want COMP vs HUMAN or COMP vs COMP */
        boolean HUMAN_PLAYER = true;
        int player = 1;

        while (!rev.ended()) {
            Pair<Integer, Move> p;
            if (player == 1) {
                //p = minimax(rev, player, 10);
                p = minimax_abeta(rev, player, 25, -Reversi.Inf, Reversi.Inf);

                System.out.println("Player " + player + " evaluates to " + p.first);
                rev.apply_move(p.second);
            }
            else {
                if (!HUMAN_PLAYER) {
                    //p = minimax(rev, player, 10);
                    p = minimax_abeta(rev, player, 25, -Reversi.Inf, Reversi.Inf);

                    System.out.println("Player " + player + " evaluates to " + p.first);
                    rev.apply_move(p.second);
                }
                else {
                    boolean valid = false;
                    while (!valid) {
                        Scanner keyboard = new Scanner(System.in);
                        System.out.print("Insert position [0..N - 1], [0..N - 1] ");

                        int x = keyboard.nextInt();
                        int y = keyboard.nextInt();

                        valid = rev.apply_move(new Move(player, x, y));
                    }
                }
            }

            System.out.print(rev);
            player *= -1;
        }

        int w = rev.winner(1);
        if (w == 1)
            System.out.println("Player 1 WON!");
        else if (w == 0)
            System.out.println("DRAW!");
        else
            System.out.println("Player -1 WON!");
    }
}
