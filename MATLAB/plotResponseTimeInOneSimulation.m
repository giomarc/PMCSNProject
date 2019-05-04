function plotResponseTimeInOneSimulation()

digits(15);
M = readmatrix('..\RESULT_OUTPUT\ResponseTimeMeansInOneSimulation.csv');

tmpRSMClass1 = M((M(:, 2) == 1), [3 4]);
tmpRSMClass2 = M((M(:, 2) == 2), [3 4]);

avgRMSClass1 =          tmpRSMClass1(:, 2);
avgRMSClass2 =          tmpRSMClass2(:, 2);

avgRMSClass1Cloudlet =  tmpRSMClass1((tmpRSMClass1(:, 1) == 1), 2);
avgRMSClass1Cloud =     tmpRSMClass1((tmpRSMClass1(:, 1) == 2), 2);

avgRMSClass2Cloudlet =  tmpRSMClass2((tmpRSMClass1(:, 1) == 1), 2);
avgRMSClass2Cloud =     tmpRSMClass2((tmpRSMClass1(:, 1) == 2), 2);

avgRMSCloudlet =        M((M(:, 3) == 1), 4);
avgRMSCloud =           M((M(:, 3) == 2), 4);

figure;
subplot(2,1,1)
histogram(avgRMSClass1);
title('Response Time Class 1');
subplot(2,1,2)
histogram(avgRMSClass2);
title('Response Time Class 2');
figure;
subplot(2,1,1)
histogram(avgRMSClass1Cloudlet);
title('Response Time Class 1 on Cloudlet');
subplot(2,1,2)
histogram(avgRMSClass1Cloud);
title('Response Time Class 1 on Cloud');
figure;
subplot(2,1,1)
histogram(avgRMSClass2Cloudlet);
title('Response Time Class 2 on Cloudlet');
subplot(2,1,2)
histogram(avgRMSClass2Cloud);
title('Response Time Class 2 on Cloud');
figure;
subplot(2,1,1)
histogram(avgRMSCloudlet);
title('Response Time on Cloudlet');
subplot(2,1,2)
h = histogram(avgRMSCloud);
title('Response Time on Cloud');






end

