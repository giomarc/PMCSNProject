function avgJobs(analyticsGlobal, analyticCloudlet, analyticCloud)
digits(15);
M = readmatrix('..\RESULT_OUTPUT\AVGjobs.csv');

avgjobsglobalSA = M(((M(:, 3) == 1) & (M(:, 7) == 3)), 8);
avgjobscloudletSA = M(((M(:, 3) == 1) & (M(:, 7) == 3)),10);
avgjobscloudSA = M(((M(:, 3) == 1) & (M(:, 7) == 3)), 9);

figure;
subplot(3,1,1);
plot(avgjobsglobalSA(:, 1));
yline(double(analyticsGlobal));
yline(mean(avgjobsglobalSA(:, 1)), 'b');
title('global avg population per iterations, standard algorithm, n=3');
xlabel('iteration');
ylabel('response time (j)');

subplot(3,1,2);
plot(avgjobscloudletSA(:, 1));
yline(double(analyticCloudlet));
yline(mean(avgjobscloudletSA(:, 1)));
title('cloudlet avg population per iterations, standard algorithm, n=3');
xlabel('iteration');
ylabel('response time (j)');

subplot(3,1,3);
plot(avgjobscloudSA(:, 1));
yline(double(analyticCloud));
yline(mean(avgjobscloudSA(:, 1)));
title('cloud avg population per iterations, standard algorithm, n=3');
xlabel('iteration');
ylabel('response time (j)');
end

