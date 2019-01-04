package com.bmd.Util;

import java.io.Serializable;

public class PVector implements Serializable {
    public float x;
    public float y;
    public float z;

    private float[] array;

    public PVector() {
    }

    public PVector(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public PVector(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public PVector set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    public PVector set(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public PVector set(PVector v) {
        x = v.x;
        y = v.y;
        z = v.z;
        return this;
    }

    public static PVector fromAngle(float angle) {
        return new PVector((float)Math.cos(angle), (float)Math.sin(angle));
    }

    public PVector copy() {
        return new PVector(x, y, z);
    }

    public float mag() {
        return (float) Math.sqrt(x*x + y*y + z*z);
    }

    public float magSq() {
        return (x*x + y*y + z*z);
    }

    public PVector add(PVector v) {
        x += v.x;
        y += v.y;
        z += v.z;
        return this;
    }

    public PVector add(float x, float y) {
        this.x += x;
        this.y += y;
        return this;
    }

    public PVector add(float x, float y, float z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    public static PVector add(PVector v1, PVector v2) {
        return add(v1, v2, null);
    }

    public static PVector add(PVector v1, PVector v2, PVector target) {
        if (target == null) {
            target = new PVector(v1.x + v2.x,v1.y + v2.y, v1.z + v2.z);
        } else {
            target.set(v1.x + v2.x, v1.y + v2.y, v1.z + v2.z);
        }
        return target;
    }

    public PVector sub(PVector v) {
        x -= v.x;
        y -= v.y;
        z -= v.z;
        return this;
    }

    public PVector sub(float x, float y) {
        this.x -= x;
        this.y -= y;
        return this;
    }

    public PVector sub(float x, float y, float z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }

    public static PVector sub(PVector v1, PVector v2) {
        return sub(v1, v2, null);
    }

    static public PVector sub(PVector v1, PVector v2, PVector target) {
        if (target == null) {
            target = new PVector(v1.x - v2.x, v1.y - v2.y, v1.z - v2.z);
        } else {
            target.set(v1.x - v2.x, v1.y - v2.y, v1.z - v2.z);
        }
        return target;
    }

    public PVector mult(float n) {
        x *= n;
        y *= n;
        z *= n;
        return this;
    }

    static public PVector mult(PVector v, float n) {
        return mult(v, n, null);
    }

    public static PVector mult(PVector v, float n, PVector target) {
        if (target == null) {
            target = new PVector(v.x*n, v.y*n, v.z*n);
        } else {
            target.set(v.x*n, v.y*n, v.z*n);
        }
        return target;
    }

    public PVector div(float n) {
        x /= n;
        y /= n;
        z /= n;
        return this;
    }

    static public PVector div(PVector v, float n) {
        return div(v, n, null);
    }

    public static PVector div(PVector v, float n, PVector target) {
        if (target == null) {
            target = new PVector(v.x/n, v.y/n, v.z/n);
        } else {
            target.set(v.x/n, v.y/n, v.z/n);
        }
        return target;
    }

    public float dist(PVector v) {
        float dx = x - v.x;
        float dy = y - v.y;
        float dz = z - v.z;
        return (float) Math.sqrt(dx*dx + dy*dy + dz*dz);
    }

    public static float dist(PVector v1, PVector v2) {
        float dx = v1.x - v2.x;
        float dy = v1.y - v2.y;
        float dz = v1.z - v2.z;
        return (float) Math.sqrt(dx*dx + dy*dy + dz*dz);
    }

    public float dot(PVector v) {
        return x*v.x + y*v.y + z*v.z;
    }

    public float dot(float x, float y, float z) {
        return this.x*x + this.y*y + this.z*z;
    }

    public static float dot(PVector v1, PVector v2) {
        return v1.x*v2.x + v1.y*v2.y + v1.z*v2.z;
    }

    public PVector cross(PVector v) {
        return cross(v, null);
    }

    public PVector cross(PVector v, PVector target) {
        float crossX = y * v.z - v.y * z;
        float crossY = z * v.x - v.z * x;
        float crossZ = x * v.y - v.x * y;

        if (target == null) {
            target = new PVector(crossX, crossY, crossZ);
        } else {
            target.set(crossX, crossY, crossZ);
        }
        return target;
    }

    public static PVector cross(PVector v1, PVector v2, PVector target) {
        float crossX = v1.y * v2.z - v2.y * v1.z;
        float crossY = v1.z * v2.x - v2.z * v1.x;
        float crossZ = v1.x * v2.y - v2.x * v1.y;

        if (target == null) {
            target = new PVector(crossX, crossY, crossZ);
        } else {
            target.set(crossX, crossY, crossZ);
        }
        return target;
    }

    public PVector normalize() {
        float m = mag();
        if (m != 0 && m != 1) {
            div(m);
        }
        return this;
    }

    public PVector normalize(PVector target) {
        if (target == null) {
            target = new PVector();
        }
        float m = mag();
        if (m > 0) {
            target.set(x/m, y/m, z/m);
        } else {
            target.set(x, y, z);
        }
        return target;
    }

    public PVector limit(float max) {
        if (magSq() > max*max) {
            normalize();
            mult(max);
        }
        return this;
    }

    public PVector setMag(float len) {
        normalize();
        mult(len);
        return this;
    }

    public PVector setMag(PVector target, float len) {
        target = normalize(target);
        target.mult(len);
        return target;
    }

    public float heading() {
        float angle = (float) Math.atan2(y, x);
        return angle;
    }

    public PVector rotate(float theta) {
        float temp = x;
        // Might need to check for rounding errors like with angleBetween function?
        x = (float)(x * Math.cos(theta) - y * Math.sin(theta));
        y = (float)(temp * Math.sin(theta) + y * Math.cos(theta));
        return this;
    }

    public static float angleBetween(PVector v1, PVector v2) {

        // We get NaN if we pass in a zero vector which can cause problems
        // Zero seems like a reasonable angle between a (0,0,0) vector and something else
        if (v1.x == 0 && v1.y == 0 && v1.z == 0 ) return 0.0f;
        if (v2.x == 0 && v2.y == 0 && v2.z == 0 ) return 0.0f;

        double dot = v1.x * v2.x + v1.y * v2.y + v1.z * v2.z;
        double v1mag = Math.sqrt(v1.x * v1.x + v1.y * v1.y + v1.z * v1.z);
        double v2mag = Math.sqrt(v2.x * v2.x + v2.y * v2.y + v2.z * v2.z);
        // This should be a number between -1 and 1, since it's "normalized"
        double amt = dot / (v1mag * v2mag);
        // But if it's not due to rounding error, then we need to fix it
        // http://code.google.com/p/processing/issues/detail?id=340
        // Otherwise if outside the range, acos() will return NaN
        // http://www.cppreference.com/wiki/c/math/acos
        if (amt <= -1) {
            return (float)Math.PI;
        } else if (amt >= 1) {
            // http://code.google.com/p/processing/issues/detail?id=435
            return 0;
        }
        return (float) Math.acos(amt);
    }

    @Override
    public String toString() {
        return "[ " + x + ", " + y + ", " + z + " ]";
    }

    public float[] array() {
        if (array == null) {
            array = new float[3];
        }
        array[0] = x;
        array[1] = y;
        array[2] = z;
        return array;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PVector)) {
            return false;
        }
        final PVector p = (PVector) obj;
        return x == p.x && y == p.y && z == p.z;
    }
    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + Float.floatToIntBits(x);
        result = 31 * result + Float.floatToIntBits(y);
        result = 31 * result + Float.floatToIntBits(z);
        return result;
    }
}
