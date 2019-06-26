function errorplot_batch_responsetime()
digits(15);
M = readmatrix('..\RESULT_OUTPUT_062019\ResponseTime.csv');

%linea_y = 4.17437;  column = 8; text  = 'Finite Horizon Response Time - Global';
%linea_y = 3.85791; column = 11; text = 'Finite Horizon Response Time - Class 1';
%linea_y = 4.47818; column = 14; text = 'Finite Horizon Response Time - Class 2';
%linea_y = 2.97808; column = 10; text = 'Finite Horizon Response Time - Cloudlet';
%linea_y = 2.22222; column = 13; text = 'Finite Horizon Response Time - Cloudlet Class 1';
%linea_y = 3.70370; column = 16; text = 'Finite Horizon Response Time - Cloudlet Class 2';
%linea_y = 4.27929; column = 9; text  = 'Finite Horizon Response Time - Cloud';
%linea_y = 4.00000; column = 12; text = 'Finite Horizon Response Time - Cloud Class 1';
linea_y = 4.54545; column = 15; text = 'Finite Horizon Response Time - Cloud Class 2';

m50   = M(((M(:, 5) == 50)),   column);
m100  = M(((M(:, 5) == 100)),  column);
m150  = M(((M(:, 5) == 150)),  column);
m200  = M(((M(:, 5) == 200)),  column);
m250  = M(((M(:, 5) == 250)),  column);
m300  = M(((M(:, 5) == 300)),  column);
m350  = M(((M(:, 5) == 350)),  column);
m400  = M(((M(:, 5) == 400)),  column);
m600  = M(((M(:, 5) == 600)),  column);
m1000 = M(((M(:, 5) == 1000)), column);
m1500 = M(((M(:, 5) == 1500)), column);
m2000 = M(((M(:, 5) == 2000)), column);
m3000 = M(((M(:, 5) == 3000)), column);
m5000 = M(((M(:, 5) == 5000)), column);


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

y = [y50 y100 y150 y200 y250 y300 y350 y400 y600 y1000 y1500 y2000 y3000];
e = [e50 e100 e150 e200 e250 e300 e350 e400 e600 e1000 e1500 e2000 e3000];
x = [50 100 150 200 250 300 350 400 600 1000 1500 2000 3000];

figure;
errorbar(x,y,e, 'k.');
%yline(4.174377);
%yline(4.278293);
yline(linea_y);
xlim([0 3100])
legend('replications', 'analytical')
title(text);
xlabel('iteration');
ylabel('response time (s)');

end


