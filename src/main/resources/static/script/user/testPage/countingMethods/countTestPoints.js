function countTestPoints() {
    let levelNames = ['A1', 'A2', 'B1', 'B2', 'C1', 'C2'];

    maxLevelPoints = countMaxPointsPerLevel();
    sumLevelPoints = countSumPointsPerLevel();

    if (sumLevelPoints[0] / maxLevelPoints[0] < 0.6) {
        return 'None';
    }

    for (var i = 1; i < 6; i++) {
        if (sumLevelPoints[i] / maxLevelPoints[i] < 0.6) {
            return levelNames[i - 1];
        }
    }

    return levelNames[5];
}

function countMaxPointsPerLevel() {
    let maxLevelPoints = [0, 0, 0, 0, 0, 0];
    let levelNames = [1, 2, 3, 4, 5, 6];
    let cycleCounter = 0;
    keys.forEach(function(element) {
        levelIndex = element.level - 1;
        if (element.correctAnswers.length === 1) {
            maxLevelPoints[levelIndex]++;
        } else {
            maxLevelPoints[levelIndex] = maxLevelPoints[levelIndex] + answers[cycleCounter].length;
        }
        cycleCounter++;
    });
    return maxLevelPoints;
}

function countSumPointsPerLevel() {
    let sumLevelPoints = [0, 0, 0, 0, 0, 0];
    let levelNames = ['A1', 'A2', 'B1', 'B2', 'C1', 'C2'];
    let cycleCounter = 0;
    keys.forEach(function(element) {
        levelIndex = element.level - 1;
        sumLevelPoints[levelIndex] = sumLevelPoints[levelIndex] + testPoints[cycleCounter];
        cycleCounter++;
    });
    return sumLevelPoints;
}
