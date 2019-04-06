function plotSteadyState
digits(15);
M = readmatrix('..\RESULT_OUTPUT\ResponseTime.csv');

analyticCloudlet = 2.978080120937264;

globaltimeBestAlgorithm = sortrows(M(M(:, 2)== 4, [4 6]), 1);
cloudletBestAlgorithm = vpa(sortrows(M(M(:, 2) == 4, [4 8]), 1));
cloudBestAlgorithm = sortrows(M(M(:, 2) == 4, [4 7]), 1);

globaltimeStandardAlgorithm = sortrows(M(M(:, 2) ~= 4, [4 7]), 1);
cloudletStandardAlgorithm = sortrows(M(M(:, 2) ~= 4, [4 9]), 1);
cloudStandardAlgorithm = sortrows(M(M(:, 2) ~= 4, [4 8]), 1);

figure;
subplot(3,1,1);
plot(globaltimeBestAlgorithm(:, 2));
title('global response time per iterations BEST ALGORITHM');
xlabel('threshold (s)');
ylabel('response time (j)');

subplot(3,1,2);
plot(cloudletBestAlgorithm(:, 2));
yline(analyticCloudlet);
title('cloudlet response time per iterations BEST ALGORITHM');
xlabel('threshold (s)');
ylabel('response time (j)');

subplot(3,1,3);
plot(cloudBestAlgorithm(:, 2));
title('cloud response time per iterations BEST ALGORITHM');
xlabel('threshold (s)');
ylabel('response time (j)');

figure;
subplot(3,1,1);
plot(globaltimeStandardAlgorithm(:, 2));
title('global response time per iterations STANDARD ALGORITHM');
xlabel('threshold (s)');
ylabel('response time (j)');

subplot(3,1,2);
plot(cloudletStandardAlgorithm(:, 2));
yline(analyticCloudlet);
title('cloudlet response time per iterations STANDARD ALGORITHM');
xlabel('threshold (s)');
ylabel('response time (j)');

subplot(3,1,3);
plot(cloudStandardAlgorithm(:, 2));
title('cloud response time per iterations STANDARD ALGORITHM');
xlabel('threshold (s)');
ylabel('response time (j)');

end
