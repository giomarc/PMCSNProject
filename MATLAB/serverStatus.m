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
if length(y)== 3
    for i1=1:numel(y)
        text(x(i1),y(i1),num2str(y(i1),'%0.6f'),...
                   'HorizontalAlignment','center',...
                   'VerticalAlignment','bottom')
    end
else
    dim = [.2 .5 .3 .3];
    txtMin = ['Min: ' num2str(min(y)) ', AVG: ' num2str(mean(y)) ', Max: ' num2str(max(y))];
    annotation('textbox',dim,'String',txtMin,'FitBoxToText','on');
end
end

