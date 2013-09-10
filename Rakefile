require 'jasmine'
load 'jasmine/tasks/jasmine.rake'

task :js_watch do
  s = "coffee -o #{__dir__}/ttt_public/javascripts/ -cw #{__dir__}/src/coffeescript/"
  puts s
  exec s
end
