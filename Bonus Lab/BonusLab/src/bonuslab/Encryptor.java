/*
 * This code is written by John Trapp and Brendan Bellows
 * for the express purpose to be used in CSCI476 at Montana State University
 * Spring 2016. Please do not use elsewhere.
 */
package bonuslab;

import java.util.Arrays;

/**
 *
 * @author John Trapp and Brendan Bellows
 */
public class Encryptor {

    private int ROUNDS;
    private byte[] text;
    private int[][] state = new int[4][4];
    private int[][] sBox = {{0x63, 0x7C, 0x77, 0x7B, 0xF2, 0x6B, 0x6F, 0xC5, 0x30, 0x01, 0x67, 0x2B, 0xFE, 0xD7, 0xAB, 0x76},
    {0xCA, 0x82, 0xC9, 0x7D, 0xFA, 0x59, 0x47, 0xF0, 0xAD, 0xD4, 0xA2, 0xAF, 0x9C, 0xA4, 0x72, 0xC0},
    {0xB7, 0xFD, 0x93, 0x26, 0x36, 0x3F, 0xF7, 0xCC, 0x34, 0xA5, 0xE5, 0xF1, 0x71, 0xD8, 0x31, 0x15},
    {0x04, 0xC7, 0x23, 0xC3, 0x18, 0x96, 0x05, 0x9A, 0x07, 0x12, 0x80, 0xE2, 0xEB, 0x27, 0xB2, 0x75},
    {0x09, 0x83, 0x2C, 0x1A, 0x1B, 0x6E, 0x5A, 0xA0, 0x52, 0x3B, 0xD6, 0xB3, 0x29, 0xE3, 0x2F, 0x84},
    {0x53, 0xD1, 0x00, 0xED, 0x20, 0xFC, 0xB1, 0x5B, 0x6A, 0xCB, 0xBE, 0x39, 0x4A, 0x4C, 0x58, 0xCF},
    {0xD0, 0xEF, 0xAA, 0xFB, 0x43, 0x4D, 0x33, 0x85, 0x45, 0xF9, 0x02, 0x7F, 0x50, 0x3C, 0x9F, 0xA8},
    {0x51, 0xA3, 0x40, 0x8F, 0x92, 0x9D, 0x38, 0xF5, 0xBC, 0xB6, 0xDA, 0x21, 0x10, 0xFF, 0xF3, 0xD2},
    {0xCD, 0x0C, 0x13, 0xEC, 0x5F, 0x97, 0x44, 0x17, 0xC4, 0xA7, 0x7E, 0x3D, 0x64, 0x5D, 0x19, 0x73},
    {0x60, 0x81, 0x4F, 0xDC, 0x22, 0x2A, 0x90, 0x88, 0x46, 0xEE, 0xB8, 0x14, 0xDE, 0x5E, 0x0B, 0xDB},
    {0xE0, 0x32, 0x3A, 0x0A, 0x49, 0x06, 0x24, 0x5C, 0xC2, 0xD3, 0xAC, 0x62, 0x91, 0x95, 0xE4, 0x79},
    {0xE7, 0xC8, 0x37, 0x6D, 0x8D, 0xD5, 0x4E, 0xA9, 0x6C, 0x56, 0xF4, 0xEA, 0x65, 0x7A, 0xAE, 0x08},
    {0xBA, 0x78, 0x25, 0x2E, 0x1C, 0xA6, 0xB4, 0xC6, 0xE8, 0xDD, 0x74, 0x1F, 0x4B, 0xBD, 0x8B, 0x8A},
    {0x70, 0x3E, 0xB5, 0x66, 0x48, 0x03, 0xF6, 0x0E, 0x61, 0x35, 0x57, 0xB9, 0x86, 0xC1, 0x1D, 0x9E},
    {0xE1, 0xF8, 0x98, 0x11, 0x69, 0xD9, 0x8E, 0x94, 0x9B, 0x1E, 0x87, 0xE9, 0xCE, 0x55, 0x28, 0xDF},
    {0x8C, 0xA1, 0x89, 0x0D, 0xBF, 0xE6, 0x42, 0x68, 0x41, 0x99, 0x2D, 0x0F, 0xB0, 0x54, 0xBB, 0x16}};
    private int[][] tran_matrix;

    public Encryptor() {
        createTran_Matrix();
    }

    public void encrypt(byte[] plainTextIn, int rounds) {
        ROUNDS = rounds;
        text = plainTextIn;

        copyInTest(state, text);
        for (int p = 0; p < state.length; p++) {
            for (int j = 0; j < state[0].length; j++) {
                System.out.print(" " + Integer.toHexString(state[p][j]).toUpperCase() + " |");
            }
            System.out.println();
        }
        System.out.println();
        for (int i = 1; i <= ROUNDS; i++) {
            subBytes();
            shiftRows();
            if (i != ROUNDS) {
                mixColumns();
                System.out.println("\nAfter " + i + " round(s), the state is");
                System.out.println(this.actualToString());
            } else {
                System.out.println("\nAfter " + i + " rounds (no MixColumn step), the state is");
                System.out.println(this.actualToString());
            }
        }
    }

    private void subBytes() {
        byte row, col;
        byte LSNMask = 0x0F;

        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[0].length; j++) {
                col = (byte) (((byte) state[i][j]) & LSNMask);
                System.out.println(col);
                row = (byte) ((((byte) state[i][j]) >>> 4) & LSNMask);
                System.out.println(row);
                state[i][j] = sBox[col][row];
            }
        }
        for (int p = 0; p < state.length; p++) {
            for (int j = 0; j < state[0].length; j++) {
                System.out.print(" " + Integer.toHexString(state[p][j]).toUpperCase() + " |");
            }
            System.out.println();
        }
        System.out.println();
    }

    private void shiftRows() {
        int[][] temp = new int[state.length][state[0].length];
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[0].length; j++) {
                temp[i][j] = state[i][Math.abs(j + i) % 4];
            }
        }

        state = temp;
        
        
        for (int p = 0; p < state.length; p++) {
            for (int j = 0; j < state[0].length; j++) {
                System.out.print(" " + Integer.toHexString(state[p][j]).toUpperCase() + " |");
            }
            System.out.println();
        }
        System.out.println();
    }

    private void mixColumns() {
        state = multiplyMatrix(state, tran_matrix);
        for (int p = 0; p < state.length; p++) {
            for (int j = 0; j < state[0].length; j++) {
                System.out.print(" " + Integer.toHexString(state[p][j]).toUpperCase() + " |");
            }
            System.out.println();
        }
        System.out.println();
    }

    private void createTran_Matrix() {
        tran_matrix = new int[4][4];
        int z = 0;
        int[] temp = new int[]{2, 3, 1, 1, 1, 2, 3, 1, 1, 1, 2, 3, 3, 1, 1, 2};
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                tran_matrix[i][j] = temp[z];
                z++;
            }
        }
    }

    private void copyIn(byte[][] state, byte[] in) {
        int inLoc = 0;
        for (int c = 0; c < 4; c++) {
            for (int r = 0; r < 4; r++) {
                state[r][c] = in[inLoc++];
            }
        }
    }

    private void copyInTest(int[][] state, byte[] in) {
        int inLoc = 0;
        for (int c = 0; c < 4; c++) {
            for (int r = 0; r < 4; r++) {
                state[r][c] = in[inLoc++];
            }
        }
    }

    private void copyOut(byte[] out, byte[][] state) {
        int outLoc = 0;
        for (int c = 0; c < 4; c++) {
            for (int r = 0; r < 4; r++) {
                out[outLoc++] = state[r][c];
            }
        }
    }

    private int[][] multiplyMatrix(int[][] inA, int[][] inB) {
        for (int p = 0; p < inA.length; p++) {
            for (int j = 0; j < inA[0].length; j++) {
                System.out.print(" " + Integer.toHexString(inA[p][j]).toUpperCase() + " |");
            }
            System.out.print("\t");
            for (int j = 0; j < inB[0].length; j++) {
                System.out.print(" " + Integer.toHexString(inB[p][j]).toUpperCase() + " |");
            }
            System.out.println();
        }
        System.out.println();
        int[][] c = new int[inA.length][inB[0].length];
        try {
            for (int i = 0; i < inA.length; i++) {
                for (int j = 0; j < inB[0].length; j++) {
                    for (int k = 0; k < inB[0].length; k++) {
                        c[i][j] += inA[i][k] * inB[k][j];
                        c[i][j] = c[i][j] & 0x000000FF;
                    }
                }
            }

        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("You are multiplying invalid matrices, idiot!");

        }
        for (int p = 0; p < state.length; p++) {
            for (int j = 0; j < state[0].length; j++) {
                System.out.print(" " + Integer.toHexString(c[p][j]).toUpperCase() + " |");
            }
            System.out.println();
        }
        System.out.println();
        return c;
    }

    private String actualToString() {
        String temp = "";

        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[0].length; j++) {
                temp += Integer.toHexString(state[i][j]).toUpperCase();
                temp += " ";
            }
        }

        return temp;
    }

}
