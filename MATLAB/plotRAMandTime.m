function plotRAMandTime()
M = readmatrix('..\RESULT_OUTPUT\SystemSimulation.csv');

RAMSIM = sortrows(M(M(:, 3) == 1, [9 10]), 1);
RAMREAL = sortrows(M(M(:, 3) == 1, [8 10]), 1);
SIMREAL = sortrows(M(M(:, 3) == 1, [8 9]), 1);

x = SIMREAL(:, 1);
y = SIMREAL(:, 2);

P = polyfit(x,y,1);
yfit = P(1)*x+P(2);
sortrows(RAMREAL(:, 2), 1);

figure;
plot(SIMREAL(:, 1), SIMREAL(:, 2), '-o');
title('SIMULATION TIME - REAL TIME');
xlabel('REAL TIME (s)');
ylabel('SIMULATION TIME (s)');
hold on
plot(x, yfit);

figure;
plot(RAMSIM(:, 1), RAMSIM(:, 2), 'o');
title('RAM - SIMULATION TIME');
xlabel('SIMULATION TIME (s)');
ylabel('RAM (MB)');

figure;
plot(RAMREAL(:, 1), RAMREAL(:, 2), 'o');
title('RAM - REAL TIME');
xlabel('REAL TIME (s)');
ylabel('RAM (MB)');
end

