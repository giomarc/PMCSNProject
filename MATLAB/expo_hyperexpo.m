function expo_hyperexpo()
digits(15);
M = readmatrix('..\RESULT_OUTPUT\ResponseTime.csv');

filteredMatrix = M(((M(:, 7) == 3)), :);

expo =  filteredMatrix(((filteredMatrix(:, 1) == 1)), :);
hyperexpo =  filteredMatrix(((filteredMatrix(:, 1) == 2)), :);

expoGlobal = expo(:, 8);
expoCloud = expo(:, 9);
expoCloudlet = expo(:, 10);

hyperexpoGlobal = hyperexpo(:, 8);
hyperexpoCloud = hyperexpo(:, 9);
hyperexpoCloudlet = hyperexpo(:, 10);

a = 1:50;

figure;
plot(a, expoCloudlet(1:50, :));
hold on
plot(a, hyperexpoCloudlet(1:50, :));
yline(mean(expoCloudlet(:, 1)));
yline(mean(hyperexpoCloudlet(:, 1)));
legend('exponential', 'hyper-exponential')
title('Cloudlet response time comparison');
xlabel('iteration');
ylabel('response time (s)');

dim = [.2 .5 .3 .3];
txtMin = ['Mean Exponential: ' num2str(mean(expoCloudlet(:, 1))) ',Mean Hyper-Exponential: ' num2str(mean(hyperexpoCloudlet(:, 1)))];
annotation('textbox',dim,'String',txtMin,'FitBoxToText','on');

end


