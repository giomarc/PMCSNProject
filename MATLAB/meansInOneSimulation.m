function meansInOneSimulation(analyticsGlobal, analyticCloudlet, analyticCloud)
digits(15);
M = readmatrix('../RESULT_OUTPUT/MeansInOneSimulation.csv');

cloudletPopulation = M(:, [2 5]);
cloudPopulation = M(:, [2 8]);
globalPopulation = [M(:,2) sum([cloudletPopulation(:,2) cloudPopulation(:,2)] , 2)];

i = 1;
while i < length(cloudletPopulation)
if cloudletPopulation(i, 2) == cloudletPopulation(i+1, 2)
cloudletPopulation(i, :) = [];
else
i = i+1;
end
end

figure();

subplot(3,1,1);
plot(cloudletPopulation(:, 1), cloudletPopulation(:, 2));
yline(double(analyticCloudlet));
title('mean cloudlet population per time');
xlabel('time (s)');
ylabel('mean population (j)');
ylim([0 max(cloudletPopulation(:, 2) +1)]);

cloudPopulation = M(:, [2 8]);
i = 1;
while i < length(cloudPopulation)
if cloudPopulation(i, 2) == cloudPopulation(i+1, 2)
cloudPopulation(i, :) = [];
else
i = i+1;
end
end

subplot(3,1,2);
plot(cloudPopulation(:, 1), cloudPopulation(:, 2));
yline(double(analyticCloud));
title('mean cloud population per time');
xlabel('time (s)');
ylabel('mean population (j)');
ylim([0, max(cloudPopulation(:, 2) + 5)]);

subplot(3,1,3);
plot(globalPopulation(:, 1), globalPopulation(:, 2));
yline(double(analyticsGlobal));
title('mean global population per time');
xlabel('time (s)');
ylabel('mean population (j)');
ylim([0, max(globalPopulation(:, 2) + 5)]);
end

