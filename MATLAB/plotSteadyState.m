function plotSteadyState(analyticsGlobal, analyticCloudlet, analyticCloud)
digits(15);
M = readmatrix('..\RESULT_OUTPUT\ResponseTime.csv');

globaltimeStandardAlgorithm = M(((M(:, 3) == 1) & (M(:, 7) == 3)), 8);
cloudletStandardAlgorithm = M(((M(:, 3) == 1) & (M(:, 7) == 3)), 10);
cloudStandardAlgorithm = M(((M(:, 3) == 1) & (M(:, 7) == 3)), 9);

figure;
subplot(3,1,1);
plot(globaltimeStandardAlgorithm(:, 1));
yline(double(analyticsGlobal));
yline(mean(globaltimeStandardAlgorithm(:, 1)), 'b');
title('global response time, standard algorithm, n=3');
xlabel('iteration');
ylabel('response time (s)');

subplot(3,1,2);
plot(cloudletStandardAlgorithm(:, 1));
yline(double(analyticCloudlet));
yline(mean(cloudletStandardAlgorithm(:, 1)));
title('cloudlet response time, standard algorithm, n=3');
xlabel('iteration');
ylabel('response time (s)');

subplot(3,1,3);
plot(cloudStandardAlgorithm(:, 1));
yline(double(analyticCloud));
yline(mean(cloudStandardAlgorithm(:, 1)));
title('cloud response time, standard algorithm, n=3');
xlabel('iteration');
ylabel('response time (s)');

end
