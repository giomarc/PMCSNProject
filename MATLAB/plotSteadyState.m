function plotSteadyState(analyticsGlobal, analyticCloudlet, analyticCloud)
digits(15);
M = readmatrix('../RESULT_OUTPUT/ResponseTime.csv');

globaltimeStandardAlgorithm = sortrows(M(M(:, 3) == 1, [4 7]), 1);
cloudletStandardAlgorithm = sortrows(M(M(:, 3) == 1, [4 9]), 1);
cloudStandardAlgorithm = sortrows(M(M(:, 3) == 1, [4 8]), 1);

figure;
subplot(3,1,1);
plot(globaltimeStandardAlgorithm(:, 2));
yline(double(analyticsGlobal));
yline(mean(globaltimeStandardAlgorithm(:, 2)), 'b');
title('global response time per iterations STANDARD ALGORITHM');
xlabel('iteration');
ylabel('response time (j)');

subplot(3,1,2);
plot(cloudletStandardAlgorithm(:, 2));
yline(double(analyticCloudlet));
yline(mean(cloudletStandardAlgorithm(:, 2)));
title('cloudlet response time per iterations STANDARD ALGORITHM');
xlabel('iteration');
ylabel('response time (j)');

subplot(3,1,3);
plot(cloudStandardAlgorithm(:, 2));
yline(double(analyticCloud));
yline(mean(cloudStandardAlgorithm(:, 2)));
title('cloud response time per iterations STANDARD ALGORITHM');
xlabel('iteration');
ylabel('response time (j)');

end
