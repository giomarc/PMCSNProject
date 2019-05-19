function batchMeansPlot
digits(15);
M = readmatrix('../RESULT_OUTPUT/BatchMeans.csv');
x = M(:, 1) + 1;
y = M(:, 10);

figure();
bar(y);
title('Cloudlet AVG population batch means');
xlabel('ID');
ylabel('AVG population');
ylim([min(y)-0.01 max(y)+0.01]);
yline(mean(y));
yline(min(y));
yline(max(y));

dim = [.2 .5 .3 .3];
txtMin = ['Min: ' num2str(min(y)) ', AVG: ' num2str(mean(y)) ', Max: ' num2str(max(y))];
annotation('textbox',dim,'String',txtMin,'FitBoxToText','on');

end

