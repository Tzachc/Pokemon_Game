package api;

public class GeoLocation implements geo_location{
        private double x, y, z;

        public GeoLocation() {
            this.x=0.0;
            this.y=0.0;
            this.z=0.0;
        }

        public GeoLocation(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;

        }

        public GeoLocation(geo_location p) {
            this.x = p.x();
            this.y = p.y();
            this.z = p.z();
        }

        @Override
        public double x() {
            return x;
        }

        @Override
        public double y() {
            return y;
        }

        @Override
        public double z() {
            return z;
        }

        @Override
        public double distance(geo_location g) {
            double xd = Math.pow(x - g.x(), 2);
            double yd = Math.pow(y - g.y(), 2);
            double zd = Math.pow(z - g.z(), 2);
            return Math.sqrt(xd + yd + zd);
        }
}
