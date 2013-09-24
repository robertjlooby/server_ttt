require 'jasmine'
load 'jasmine/tasks/jasmine.rake'

task :source_watch do
  s = "coffee -o #{__dir__}/ttt_public/javascripts/ -cw #{__dir__}/src/coffeescripts/"
  puts s
  exec s
end

task :spec_watch do
  s = "coffee -cw #{__dir__}/spec/javascripts/"
  puts s
  exec s
end
