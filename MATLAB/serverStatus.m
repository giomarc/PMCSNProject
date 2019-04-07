function serverStatus
digits(15);
M = readmatrix('..\RESULT_OUTPUT\ServerStatus.csv');
x = M(:, 7) + 1;
y = M(:, 8);
figure();
bar(y);
title('Server utilization');
xlabel('ID');
ylabel('utilization');
ylim([min(y)-0.05 max(y)+0.05]);
for i1=1:numel(y)
    text(x(i1),y(i1),num2str(y(i1),'%0.6f'),...
               'HorizontalAlignment','center',...
               'VerticalAlignment','bottom')
end
end

