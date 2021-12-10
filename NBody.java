public class NBody {
    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]);
        double deltaT = Double.parseDouble(args[1]);
        int n = StdIn.readInt();
        double radius = StdIn.readDouble();

        double[] Fx = new double[n];
        double[] Fy = new double[n];
        double[] ax = new double[n];
        double[] ay = new double[n];
        double[] vx = new double[n];
        double[] vy = new double[n];
        double[] px = new double[n];
        double[] py = new double[n];
        double[] mass = new double[n];
        String[] image = new String[n];
        double G = 6.67e-11;

        for (int i = 0; i < n; i++) {
            px[i] = StdIn.readDouble();
            py[i] = StdIn.readDouble();
            vx[i] = StdIn.readDouble();
            vy[i] = StdIn.readDouble();
            mass[i] = StdIn.readDouble();
            image[i] = StdIn.readString();
        }

        StdDraw.setXscale(-radius, +radius);
        StdDraw.setYscale(-radius, +radius);
        StdDraw.enableDoubleBuffering();
        StdAudio.play("2001.wav");

        for (double t = 0; t < T; t += deltaT) {

            for (int i = 0; i < n; i++) {
                Fx[i] = 0;
                Fy[i] = 0;
            }
            for (int i = 0; i < n; i++) {
                // total force on planet i
                for (int j = 0; j < n; j++) {
                    if (i != j) {
                        double deltaX = px[j] - px[i];
                        double deltaY = py[j] - py[i];
                        double r = deltaX * deltaX + deltaY * deltaY;
                        double F = G * mass[i] * mass[j] / r;
                        Fx[i] += F * deltaX / Math.sqrt(r);
                        Fy[i] += F * deltaY / Math.sqrt(r);
                    }
                }
            }

            for (int i = 0; i < n; i++) {
                ax[i] = Fx[i] / mass[i];
                ay[i] = Fy[i] / mass[i];
                vx[i] += ax[i] * deltaT;
                vy[i] += ay[i] * deltaT;
                px[i] += vx[i] * deltaT;
                py[i] += vy[i] * deltaT;
            }

            StdDraw.picture(0, 0, "starfield.jpg");
            for (int i = 0; i < n; i++) {
                StdDraw.picture(px[i], py[i], image[i]);
            }
            StdDraw.show();
            StdDraw.pause(0);
        }

        // final output
        StdOut.printf("%d\n", n);
        StdOut.printf("%.2e\n", radius);
        for (
                int i = 0;
                i < n; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                          px[i], py[i], vx[i], vy[i], mass[i], image[i]);
        }
    }
}
