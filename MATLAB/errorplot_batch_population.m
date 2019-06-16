function errorplot_batch_population()
digits(15);
M = readmatrix('..\RESULT_OUTPUT\AVGjobs.csv');

m50   = M(((M(:, 5) == 50)), 8);
m100  = M(((M(:, 5) == 100)), 8);
m150  = M(((M(:, 5) == 150)), 8);
m200  = M(((M(:, 5) == 200)), 8);
m250  = M(((M(:, 5) == 250)), 8);
m300  = M(((M(:, 5) == 300)), 8);
m350  = M(((M(:, 5) == 350)), 8);
m400  = M(((M(:, 5) == 400)), 8);
m600  = M(((M(:, 5) == 600)), 8);
m1000 = M(((M(:, 5) == 1000)), 8);
m1500 = M(((M(:, 5) == 1500)), 8);
m2000 = M(((M(:, 5) == 2000)), 8);
m3000 = M(((M(:, 5) == 3000)), 8);
m5000 = M(((M(:, 5) == 5000)), 8);


y50 = mean(m50);
e50 = std(m50);
y100 = mean(m100);
e100 = std(m100);
y150 = mean(m150);
e150 = std(m150);
y200 = mean(m200);
e200 = std(m200);
y250 = mean(m250);
e250 = std(m250);
y300 = mean(m300);
e300 = std(m300);
y350 = mean(m350);
e350 = std(m350);
y400 = mean(m400);
e400 = std(m400);
y600 = mean(m600);
e600 = std(m600);
y1000 = mean(m1000);
e1000 = std(m1000);
y1500 = mean(m1500);
e1500 = std(m1500);
y2000 = mean(m2000);
e2000 = std(m2000);
y3000 = mean(m3000);
e3000 = std(m3000);
y5000 = mean(m5000);
e5000 = std(m5000);

y = [y50 y100 y150 y200 y250 y300 y350 y400 y600 y1000 y1500 y2000 y3000 y5000];
e = [e50 e100 e150 e200 e250 e300 e350 e400 e600 e1000 e1500 e2000 e3000 e5000];
x=[50 100 150 200 250 300 350 400 600 1000 1500 2000 3000 5000];


figure;
errorbar(x,y,e, 'k.');
yline(51.136112);
xlim([0 3100])
legend('replications', 'analytical')
title('Finite Horizon Population');
xlabel('iteration');
ylabel('Population (j)');

end


