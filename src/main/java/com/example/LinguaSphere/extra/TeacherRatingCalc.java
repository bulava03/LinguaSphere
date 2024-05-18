package com.example.LinguaSphere.extra;

import com.example.LinguaSphere.entity.TeacherParams;

import java.util.*;

public class TeacherRatingCalc {

    static double satisfactionWeight;
    static double experienceWeight;
    static double certificatesWeight;

    public static void calculateWeights() {
        List<TeacherParams> teachers = new ArrayList<>();
        teachers.add(new TeacherParams(0.8, 5, 3));
        teachers.add(new TeacherParams(0.7, 3, 2));
        teachers.add(new TeacherParams(0.9, 10, 4));

        double[][] X = new double[teachers.size()][3];
        double[] Y = new double[teachers.size()];

        double[] temp = new double[] { 7.3, 4.7, 12.9 };

        for (int i = 0; i < teachers.size(); i++) {
            X[i][0] = teachers.get(i).getSatisfaction();
            X[i][1] = teachers.get(i).getTeachingExperience();
            X[i][2] = teachers.get(i).getCertificates();
            Y[i] = temp[i]; // Рейтинг вчителя
        }

        double[] weights = leastSquares(X, Y);

        satisfactionWeight = weights[0];
        experienceWeight = weights[1];
        certificatesWeight = weights[2];
    }

    private static double mapValue(double x, double xMin, double xMax, double yMin, double yMax) {
        return ((x - xMin) / (xMax - xMin)) * (yMax - yMin) + yMin;
    }

    public static List<TeacherParams> sortTeacherByParams(List<TeacherParams> teachers) {
        double maxSatisfaction = teachers.stream()
                .mapToDouble(TeacherParams::getSatisfaction)
                .max()
                .getAsDouble();
        double minSatisfaction = teachers.stream()
                .mapToDouble(TeacherParams::getSatisfaction)
                .min()
                .getAsDouble();

        double maxCertificate = teachers.stream()
                .mapToDouble(TeacherParams::getCertificates)
                .max()
                .getAsDouble();
        double minCertificate = teachers.stream()
                .mapToDouble(TeacherParams::getCertificates)
                .min()
                .getAsDouble();

        double maxExperience = teachers.stream()
                .mapToDouble(TeacherParams::getTeachingExperience)
                .max()
                .getAsDouble();
        double minExperience = teachers.stream()
                .mapToDouble(TeacherParams::getTeachingExperience)
                .min()
                .getAsDouble();

        // Сортуємо викладачів за згенерованим рейтингом
        teachers.sort(new Comparator<TeacherParams>() {
            @Override
            public int compare(TeacherParams t1, TeacherParams t2) {
                double score1 = satisfactionWeight * mapValue(t1.getSatisfaction(), minSatisfaction, maxSatisfaction, 0, 100)
                        + experienceWeight * mapValue(t1.getTeachingExperience(), minExperience, maxExperience, 0, 100)
                        + certificatesWeight * mapValue(t1.getCertificates(), minCertificate, maxCertificate, 0, 100);
                double score2 = satisfactionWeight * mapValue(t2.getSatisfaction(), minSatisfaction, maxSatisfaction, 0, 100)
                        + experienceWeight * mapValue(t2.getTeachingExperience(), minExperience, maxExperience, 0, 100)
                        + certificatesWeight * mapValue(t2.getCertificates(), minCertificate, maxCertificate, 0, 100);
                return Double.compare(score2, score1);
            }
        });

        return teachers;
    }

    private static double[] leastSquares(double[][] X, double[] Y) {
        int n = X[0].length;
        double[] weights = new double[n];

        // Реалізуйте метод найменших квадратів тут
        double[][] Xt = transposeMatrix(X);
        double[][] XtX = multiplyMatrices(Xt, X);
        double[][] XtX_inv = invertMatrix(XtX);
        double[][] XtY = multiplyMatrices(Xt, transposeMatrix(new double[][]{Y}));
        double[][] W = multiplyMatrices(XtX_inv, XtY);

        for (int i = 0; i < W.length; i++) {
            weights[i] = W[i][0];
        }

        return weights;
    }

    private static double[][] transposeMatrix(double[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        double[][] transposedMatrix = new double[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                transposedMatrix[i][j] = matrix[j][i];
            }
        }
        return transposedMatrix;
    }

    private static double[][] multiplyMatrices(double[][] firstMatrix, double[][] secondMatrix) {
        int r1 = firstMatrix.length;
        int c1 = firstMatrix[0].length;
        int c2 = secondMatrix[0].length;
        double[][] product = new double[r1][c2];
        for (int i = 0; i < r1; i++) {
            for (int j = 0; j < c2; j++) {
                for (int k = 0; k < c1; k++) {
                    product[i][j] += firstMatrix[i][k] * secondMatrix[k][j];
                }
            }
        }
        return product;
    }

    private static double[][] invertMatrix(double[][] matrix) {
        int n = matrix.length;
        double[][] augmentedMatrix = new double[n][2 * n];
        double[][] inverse = new double[n][n];

        // Створюємо розширену матрицю [A|I]
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                augmentedMatrix[i][j] = matrix[i][j];
                augmentedMatrix[i][j + n] = (i == j) ? 1 : 0;
            }
        }

        // Виконуємо метод Гаусса-Жордана
        for (int i = 0; i < n; i++) {
            double pivot = augmentedMatrix[i][i];
            for (int j = 0; j < 2 * n; j++) {
                augmentedMatrix[i][j] /= pivot;
            }
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    double ratio = augmentedMatrix[j][i];
                    for (int k = 0; k < 2 * n; k++) {
                        augmentedMatrix[j][k] -= ratio * augmentedMatrix[i][k];
                    }
                }
            }
        }

        // Витягуємо обернену матрицю
        for (int i = 0; i < n; i++) {
            System.arraycopy(augmentedMatrix[i], n, inverse[i], 0, n);
        }

        return inverse;
    }

}
