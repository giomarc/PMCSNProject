function avgJobs(analyticsGlobal, analyticCloudlet, analyticCloud)
digits(15);
M = readmatrix('../RESULT_OUTPUT/AVGjobs.csv');

avgjobsglobalSA = sortrows(M(M(:, 3) == 1, [4 7]), 1);
avgjobscloudletSA = sortrows(M(M(:, 3) == 1, [4 9]), 1);
avgjobscloudSA = sortrows(M(M(:, 3) == 1, [4 8]), 1);

figure;
subplot(3,1,1);
plot(avgjobsglobalSA(:, 2));
yline(double(analyticsGlobal));
yline(mean(avgjobsglobalSA(:, 2)), 'b');
title('global avg population per iterations STANDARD ALGORITHM');
xlabel('iteration');
ylabel('response time (j)');

subplot(3,1,2);
plot(avgjobscloudletSA(:, 2));
yline(double(analyticCloudlet));
yline(mean(avgjobscloudletSA(:, 2)));
title('cloudlet avg population per iterations STANDARD ALGORITHM');
xlabel('iteration');
ylabel('response time (j)');

subplot(3,1,3);
plot(avgjobscloudSA(:, 2));
yline(double(analyticCloud));
yline(mean(avgjobscloudSA(:, 2)));
title('cloud avg population per iterations STANDARD ALGORITHM');
xlabel('iteration');
ylabel('response time (j)');
end

